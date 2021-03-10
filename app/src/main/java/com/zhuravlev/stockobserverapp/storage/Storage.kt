package com.zhuravlev.stockobserverapp.storage

import android.content.Context
import androidx.room.Room
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponseMarketData
import com.zhuravlev.stockobserverapp.model.moex.ResponseSecurities
import com.zhuravlev.stockobserverapp.model.moex.Security
import com.zhuravlev.stockobserverapp.model.moex.converters.parseResponseMarketData
import com.zhuravlev.stockobserverapp.model.moex.converters.parseSecurityList
import com.zhuravlev.stockobserverapp.storage.database.AppDatabase
import com.zhuravlev.stockobserverapp.storage.database.StockDAO
import com.zhuravlev.stockobserverapp.storage.net.getMoexApiService
import com.zhuravlev.stockobserverapp.ui.Refreshable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Storage(applicationContext: Context) {
    private val mDatabase: AppDatabase
    private val mStockDao: StockDAO
    private var mRefreshable: Refreshable? = null

    init {
        instance = this
        mDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-stock-observer"
        ).build()
        mStockDao = mDatabase.stockDao()
        downloadStocks()
    }

    companion object {
        var instance: Storage? = null
    }

    private fun getIoPrice(
        onSuccess: (List<ResponseMarketData>) -> Unit
    ) {
        getMoexApiService().getMarketData().requestIo(onSuccess, {})
    }

    fun updatePrices() {
        toMainThread { mRefreshable?.startRefresh() }
        getIoPrice {
            parseResponseMarketData(it).requestIo({ map ->
                synchronizePriceStocks(map).requestIo({}, {})
            }, {})
        }
    }

    private fun getAllStocks(
        onSuccess: (List<ResponseSecurities>) -> Unit
    ) {
        getMoexApiService().getSecurities()
            .requestIo(onSuccess, { mRefreshable?.showError(it.message ?: "error") })
    }

    /**
     * Есть загрузка данных из бд, но нет записи в бд, так как данные пока без цен
     */
    private fun downloadStocks() {
        getAllStocks {
            val list = mutableListOf<Security?>()
            it.forEach { responseItem ->
                if (responseItem.securities != null) {
                    list.addAll(responseItem.securities)
                }
            }
            parseSecurityList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { stocks ->
                    updateStocks(stocks)
                    updatePrices()
                }
        }
    }

    private fun updateStocks(list: List<Stock>) {
        mStockDao.getSingleStocks()
            .requestIo({
                if (it.isEmpty()) {
                    mStockDao.insertStocks(list).subscribe { }
                } else {
                    list.forEach { stock ->
                        if (!it.contains(stock)) {
                            mStockDao.insert(stock).subscribe { }
                        }
                    }
                }
            }, {})
    }

    private fun saveStocks(stocks: List<Stock>) {
        mStockDao.insertStocks(stocks)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { }
    }

    private fun synchronizePriceStocks(map: Map<String, Pair<String, String>>): Single<Unit> {
        return Single.fromCallable {
            mStockDao.getSingleStocks()
                .requestIo({
                    it.forEach { stock ->
                        if (map.containsKey(stock.symbol)) {
                            val pair = map[stock.symbol]!!
                            stock.price = pair.first
                            stock.changePrice = pair.second
                        }
                    }
                    saveStocks(it)
                    toMainThread { mRefreshable?.endRefresh() }
                    Thread.sleep(20_000)
                    toMainThread { mRefreshable?.hideRefresh() }
                    Thread.sleep(1_200_000)
                    toMainThread { mRefreshable?.showRefreshButton() }
                }, {})
        }
    }

    fun getFavouritesStocks(query: String = ""): Flowable<MutableList<Stock>> {
        if (query.isEmpty()) {
            return mStockDao.getFavouritesStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
        val que = "%$query%"
        return mStockDao.getFavouritesStocksByQuery(que)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getStocks(query: String = ""): Flowable<MutableList<Stock>> {
        if (query.isEmpty()) {
            return mStockDao.getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
        val que = "%$query%"
        return mStockDao.getStocksByQuery(que)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun changeStock(stock: Stock) {
        mStockDao.update(stock)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { }
    }

    fun setRefreshable(refreshable: Refreshable) {
        mRefreshable = refreshable
    }
}

private fun toMainThread(action: () -> Unit) {
    Single.fromCallable { }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe { s -> action() }
}

private fun <T> Single<T>.requestIo(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(onSuccess, onError)
}