package com.zhuravlev.stockobserverapp.storage

import android.content.Context
import androidx.room.Room
import com.zhuravlev.stockobserverapp.model.PriceChart
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponseMarketData
import com.zhuravlev.stockobserverapp.model.moex.ResponseSecurities
import com.zhuravlev.stockobserverapp.model.moex.Security
import com.zhuravlev.stockobserverapp.model.moex.converters.parseResponseCandles
import com.zhuravlev.stockobserverapp.model.moex.converters.parseResponseMarketData
import com.zhuravlev.stockobserverapp.model.moex.converters.parseSecurityList
import com.zhuravlev.stockobserverapp.storage.database.AppDatabase
import com.zhuravlev.stockobserverapp.storage.database.MIGRATION_1_2
import com.zhuravlev.stockobserverapp.storage.database.StockDAO
import com.zhuravlev.stockobserverapp.storage.net.getMoexApiService
import com.zhuravlev.stockobserverapp.ui.Shower
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class Storage(applicationContext: Context) {
    private val mDatabase: AppDatabase
    private val mStockDao: StockDAO
    private var mShower: Shower? = null

    init {
        instance = this
        mDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-stock-observer"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
        mStockDao = mDatabase.stockDao()
        downloadStocks()
    }

    companion object {
        var instance: Storage? = null
    }

    private fun showError(throwable: Throwable) {
        // Здесь можно обрабатывать различные ошибки, но сейчас актуальны только ошибки с подключением
        toMainThread { mShower?.showError(throwable.message ?: "Error") }
    }

    private fun hideError() {
        toMainThread { mShower?.hideError() }
    }

    private fun getIoPrice(
        onSuccess: (List<ResponseMarketData>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getMoexApiService().getMarketData().requestIo(onSuccess, onError)
    }

    private fun updatePrices() {
        getIoPrice({
            parseResponseMarketData(it).requestIo({ map ->
                synchronizePriceStocks(map).requestIo({}, {})
            }, { showError(it) })
        }, { showError(it) })
    }

    fun updatePricesCallback(onEnd: () -> Unit) {
        getIoPrice({
            parseResponseMarketData(it)
                .subscribe({ map ->
                    synchronizePriceStocks(map)
                        .subscribe({ onEnd() }, { showError(it);onEnd() })
                }, { showError(it);onEnd() })
        }, { showError(it);onEnd() })
    }

    private fun getAllStocks(
        onSuccess: (List<ResponseSecurities>) -> Unit
    ) {
        getMoexApiService().getSecurities()
            .requestIo(onSuccess, { showError(it) })
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
                        val index = it.indexOf(stock)
                        if (index == -1) {
                            mStockDao.insert(stock).subscribe { }
                        } else {
                            if (it[index].enDescription != stock.enDescription || it[index].description != stock.description) {
                                it[index].enDescription = stock.enDescription
                                it[index].description = stock.description
                                mStockDao.update(it[index]).subscribe { }
                            }
                        }
                    }
                }
            }, { showError(it) })
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
                    hideError()
                }, { showError(it) })
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
            .subscribe { hideError() }
    }

    fun setShower(shower: Shower) {
        mShower = shower
    }

    fun getCandle(stock: Stock, from: Date, onSuccess: (PriceChart) -> Unit) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date: String = dateFormat.format(from)

        getMoexApiService().getCandles(stock.symbol, date)
            .requestIo({
                parseResponseCandles(it, stock).subscribe({ chart ->
                    onSuccess(chart)
                }, {
                    hideError()
                })
            }, {
                hideError()
            })
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