package com.zhuravlev.stockobserverapp.utils

import com.zhuravlev.stockobserverapp.model.Stock
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class RxBus {
    private val mBus = PublishSubject.create<Stock>()

    fun send(event: Stock) {
        mBus.onNext(event)
    }

    fun toObservable(): Observable<Stock> {
        return mBus
    }

    fun hasObservers(): Boolean {
        return mBus.hasObservers()
    }
}