package com.zhuravlev.stockobserverapp.storage

import android.content.Context
import androidx.room.Room
import com.zhuravlev.stockobserverapp.model.Stock
import com.zhuravlev.stockobserverapp.model.moex.ResponseAllStocks
import com.zhuravlev.stockobserverapp.model.moex.ResponsePriceAllStocksByDate
import com.zhuravlev.stockobserverapp.model.moex.SecuritiesItem
import com.zhuravlev.stockobserverapp.model.moex.converters.parseResponsePriceAllStocksByDate
import com.zhuravlev.stockobserverapp.model.moex.converters.parseSecurities
import com.zhuravlev.stockobserverapp.storage.database.AppDatabase
import com.zhuravlev.stockobserverapp.storage.database.StockDAO
import com.zhuravlev.stockobserverapp.storage.net.getMoexApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class Storage(applicationContext: Context) {
    private val mDatabase: AppDatabase
    private val mStockDao: StockDAO

    init {
        instance = this
        mDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-stock-observer"
        ).build()
        mStockDao = mDatabase.stockDao()
        downloadStocks()
    }

    private fun updateStocks(list: List<Stock>) {
        mStockDao.getSingleStocks()
            .requestIo({
                var i: Int
                list.forEach { stock ->
                    i = it.indexOf(stock)
                    if (i >= 0) {
                        it[i].price = stock.price
                        it[i].changePrice = stock.changePrice
                    } else {
                        it.add(stock)
                    }
                }
                mStockDao.updateStocks(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { }
            }, {})
    }

    companion object {
        var instance: Storage? = null
    }

    private fun getIoPriceAllStocksLastDate(
        start: String,
        onSuccess: (List<ResponsePriceAllStocksByDate>) -> Unit
    ) {
        getMoexApiService().getPriceAllStocksLastDate(start = start).requestIo(onSuccess, {})
    }

    private fun updatePrices() {
        getIoPriceAllStocksLastDate("0") { item1 ->
            getIoPriceAllStocksLastDate("100") { item2 ->
                getIoPriceAllStocksLastDate("200") { item3 ->
                    parseResponsePriceAllStocksByDate(item1 + item2 + item3)
                        .requestIo({ map ->
                            synchronizePriceStocks(map).requestIo({}, {})
                        }, {})
                }
            }
        }
    }


    private fun getAllStocks(
        start: String,
        onSuccess: (List<ResponseAllStocks>) -> Unit
    ) {
        getMoexApiService().getAllStocks(start = start)
            .requestIo(onSuccess, {})
    }

    /**
     * Есть загрузка данных из бд, но нет записи в бд, так как данные пока без цен
     */
    private fun downloadStocks() {
        getAllStocks("0") { item1 ->
            getAllStocks("100") { item2 ->
                getAllStocks("200") { item3 ->
                    val items = item1 + item2 + item3
                    val list = mutableListOf<SecuritiesItem?>()
                    items.forEach { responseItem ->
                        if (responseItem.securities != null) {
                            list.addAll(responseItem.securities)
                        }
                    }
                    parseSecurities(list)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe { parseList: MutableList<Stock> ->
                            updateStocks(parseList)
                            updatePrices()
                        }
                }
            }
        }
    }

    private fun saveStocks(stocks: List<Stock>) {
        mStockDao.insertStocks(stocks)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { }
    }

    private fun synchronizePriceStocks(map: Map<String, Pair<String, String>>): Single<Unit> {
        return Single.fromCallable {
            mStockDao.getStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    it.forEach { stock ->
                        if (map.containsKey(stock.symbol)) {
                            val pair = map[stock.symbol]!!
                            stock.price = pair.first
                            stock.changePrice = String.format(
                                "%.2f",
                                (pair.first.toDouble() - pair.second.toDouble())
                            )
                        }
                    }
                    saveStocks(it)
                }
        }
    }

    fun getFavouritesStocks(): Flowable<MutableList<Stock>> {
        return mStockDao.getFavouritesStocks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getStocks(): Flowable<MutableList<Stock>> {
        return mStockDao.getStocks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun changeStock(stock: Stock) {
        mStockDao.insert(stock)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { }
    }
}


private fun <T> Single<T>.requestIo(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(onSuccess, onError)
}