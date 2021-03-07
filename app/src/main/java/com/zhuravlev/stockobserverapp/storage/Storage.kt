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
import com.zhuravlev.stockobserverapp.storage.net.getMoexApiService
import com.zhuravlev.stockobserverapp.utils.RxBus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class Storage(applicationContext: Context) {
    private val mDatabase: AppDatabase
    private lateinit var mFavourites: MutableList<Stock>
    val bus = RxBus()
    private val fav = PublishSubject.create<MutableList<Stock>>()

    init {
        instance = this
        mDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-stock-observer"
        ).build()
        mDatabase.stockDao().getFavouritesStocks().subscribe {
            mFavourites = it
        }
    }

    companion object {
        var instance: Storage? = null
    }

    private fun getIoPriceAllStocksLastDate(
        start: String,
        onSuccess: (List<ResponsePriceAllStocksByDate>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getMoexApiService().getPriceAllStocksLastDate(start = start).requestIo(onSuccess, onError)
    }

    /**
     * Не кешируется, так как только цены
     */
    fun getCurrentPrices(
        onSuccess: (Map<String, Pair<String, String>>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        getIoPriceAllStocksLastDate(
            "0", { item1 ->
                getIoPriceAllStocksLastDate(
                    "100", { item2 ->
                        getIoPriceAllStocksLastDate(
                            "200",
                            { item3 ->
                                parseResponsePriceAllStocksByDate(item1 + item2 + item3)
                                    .subscribe({ map ->
                                        synchronizePriceStocks(map).requestIo({}, {})
                                        onSuccess(map)
                                    }, onError)
                            },
                            onError
                        )
                    }, onError
                )
            }, onError
        )
    }


    private fun getAllStocks(
        start: String,
        onSuccess: (List<ResponseAllStocks>) -> Unit,
        onError: (List<Stock>, Throwable) -> Unit
    ) {
        getMoexApiService().getAllStocks(start = start).requestWithDatabase(onSuccess, onError)
    }

    /**
     * Есть загрузка данных из бд, но нет записи в бд, так как данные пока без цен
     */
    fun getStocks(
        onSuccess: (List<Stock>) -> Unit,
        onError: (List<Stock>, Throwable) -> Unit
    ) {
        getAllStocks("0", { item1 ->
            getAllStocks("100", { item2 ->
                getAllStocks("200", { item3 ->
                    val items = item1 + item2 + item3
                    val list = mutableListOf<SecuritiesItem?>()
                    items.forEach { responseItem ->
                        if (responseItem.securities != null) {
                            list.addAll(responseItem.securities)
                        }
                    }
                    parseSecurities(list).requestWithDatabase(onSuccess, onError)
                }, onError)
            }, onError)
        }, onError)
    }

    private fun saveStocks(stocks: List<Stock>) {
        mDatabase.stockDao().insertStocks(stocks)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { }
    }

    private fun loadStocks(onSuccess: (List<Stock>) -> Unit) {
        mDatabase.stockDao().getStocks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess)
    }

    private fun synchronizePriceStocks(map: Map<String, Pair<String, String>>): Single<Unit> {
        return Single.fromCallable {
            loadStocks {
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
                mDatabase.stockDao().getFavouritesStocks().subscribe {
                    mFavourites = it
                    mFavourites.forEach {
                        bus.send(it)
                    }
                }
            }
        }
    }

    fun changeFavourite(stock: Stock) {
        if (stock.star && !mFavourites.contains(stock)) {
            mFavourites.add(stock)
            fav.onNext(mFavourites)
            mDatabase.stockDao().update(stock)
            bus.send(stock)
        } else {
            if (!stock.star && mFavourites.contains(stock)) {
                mFavourites.remove(stock)
                fav.onNext(mFavourites)
                mDatabase.stockDao().update(stock)
                bus.send(stock)
            }
        }
    }

    fun getFavouritesStocks(): MutableList<Stock> {
        return mFavourites
    }

    fun getFav(): Observable<MutableList<Stock>> {
        return fav
    }
}

private fun <T> Single<T>.requestWithoutDatabase(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onSuccess, onError)
}

private fun <T> Single<T>.requestWithDatabase(
    onSuccess: (T) -> Unit,
    onError: (List<Stock>, Throwable) -> Unit
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            onSuccess(it)
        }, {
            Storage.instance?.getStocks({ list ->
                onError(list, it)
            }, { list: List<Stock>, throwable: Throwable ->
                onError(listOf(), it)
            })
        })
}

private fun <T> Single<T>.requestIo(
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(onSuccess, onError)
}