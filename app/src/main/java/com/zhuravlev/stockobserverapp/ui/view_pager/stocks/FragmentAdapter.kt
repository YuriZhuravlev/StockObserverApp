package com.zhuravlev.stockobserverapp.ui.view_pager.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.storage.Search
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.ui.fragment.list.FavouritesAdapter
import com.zhuravlev.stockobserverapp.ui.fragment.list.StocksAdapter
import io.reactivex.rxjava3.disposables.Disposable

private const val STOCKS = 0
private const val FAVOURITE = 1

class FragmentAdapter() : RecyclerView.Adapter<FragmentViewHolder>() {
    private var mStockDisposable: Disposable? = null
    private var mFavouriteDisposable: Disposable? = null
    private val mQuery = Search.instance.getQueryObservable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentViewHolder {
        return FragmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int) {
        when (getItemViewType(position)) {
            STOCKS -> {
                initStocks(holder)
            }
            FAVOURITE -> {
                initFavourite(holder)
            }
            else -> {
            }
        }
    }

    private fun refreshListener(swipeRefreshLayout: SwipeRefreshLayout) {
        Storage.instance!!.updatePricesCallback {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initFavourite(holder: FragmentViewHolder) {
        holder.adapter = FavouritesAdapter()
        holder.recyclerView.adapter = holder.adapter
        holder.swipeRefreshLayout.setOnRefreshListener {
            refreshListener(holder.swipeRefreshLayout)
        }
        mQuery.subscribe { query ->
            mFavouriteDisposable?.dispose()
            holder.adapter.clearList()
            mFavouriteDisposable = Storage.instance!!.getFavouritesStocks(query).subscribe {
                holder.adapter.addList(it)
            }
        }
        Search.instance.getQuery()
    }

    private fun initStocks(holder: FragmentViewHolder) {
        holder.adapter = StocksAdapter()
        holder.recyclerView.adapter = holder.adapter
        holder.swipeRefreshLayout.setOnRefreshListener {
            refreshListener(holder.swipeRefreshLayout)
        }
        mQuery.subscribe { query ->
            mStockDisposable?.dispose()
            holder.adapter.clearList()
            mStockDisposable = Storage.instance!!.getStocks(query).subscribe {
                holder.adapter.addList(it)
            }
        }
        Search.instance.getQuery()
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mFavouriteDisposable?.dispose()
        mStockDisposable?.dispose()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}