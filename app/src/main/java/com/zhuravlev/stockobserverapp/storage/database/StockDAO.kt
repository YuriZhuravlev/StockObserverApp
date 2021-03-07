package com.zhuravlev.stockobserverapp.storage.database

import androidx.room.*
import com.zhuravlev.stockobserverapp.model.Stock
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface StockDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stock: Stock): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStocks(list: List<Stock>): Completable

    @Update
    fun update(stock: Stock): Completable

    @Delete
    fun delete(stock: Stock): Completable

    @Query("SELECT * FROM stock WHERE stock.symbol == :symbol")
    fun getStockBySymbol(symbol: String): Single<Stock>

    @Query("SELECT * FROM stock ORDER BY stock.symbol")
    fun getStocks(): Flowable<List<Stock>>
}