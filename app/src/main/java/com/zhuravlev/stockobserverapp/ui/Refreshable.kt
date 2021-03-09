package com.zhuravlev.stockobserverapp.ui

interface Refreshable {
    fun startRefresh()
    fun endRefresh()
    fun hideRefresh()
    fun showRefreshButton()
    fun showError(message: String)
}