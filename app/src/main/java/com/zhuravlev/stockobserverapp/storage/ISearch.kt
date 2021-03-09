package com.zhuravlev.stockobserverapp.storage

import io.reactivex.rxjava3.core.Observable

interface ISearch {
    val query: Observable<String>
}