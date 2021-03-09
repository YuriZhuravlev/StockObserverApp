package com.zhuravlev.stockobserverapp.storage

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

class Search private constructor() {
    private var mSearch: ISearch? = null
    private var mSearchDisposable: Disposable? = null
    private var mQuery = ""
    private val mQueryObservable = PublishSubject.create<String>()

    fun setSearchView(search: ISearch) {
        mSearchDisposable?.dispose()
        mSearch = search
        mSearchDisposable = mSearch!!.query.subscribe {
            if (mQuery != it) {
                mQuery = it
                mQueryObservable.onNext(mQuery)
            }
        }
    }

    fun getQueryObservable(): Observable<String> {
        return mQueryObservable
    }

    companion object {
        var instance: Search = Search()
    }
}