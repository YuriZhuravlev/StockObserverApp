package com.zhuravlev.stockobserverapp.ui.view_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zhuravlev.stockobserverapp.R
import com.zhuravlev.stockobserverapp.storage.Search
import com.zhuravlev.stockobserverapp.storage.Storage
import com.zhuravlev.stockobserverapp.ui.fragment.fragment_list.FavouritesAdapter
import com.zhuravlev.stockobserverapp.ui.fragment.fragment_list.StocksAdapter
import io.reactivex.rxjava3.disposables.Disposable

private const val STOCKS = 0
private const val FAVOURITE = 1

class FragmentAdapter(list: List<Fragment>) : RecyclerView.Adapter<FragmentViewHolder>() {
    private val mList = list
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
                initStocks(holder, mList[position])
            }
            FAVOURITE -> {
                initFavourite(holder, mList[position])
            }
            else -> {
            }
        }
    }

    private fun initFavourite(holder: FragmentViewHolder, item: Fragment) {
        holder.adapter = FavouritesAdapter()
        holder.recyclerView.adapter = holder.adapter
        mQuery.subscribe { query ->
            mFavouriteDisposable?.dispose()
            holder.adapter.clearList()
            mFavouriteDisposable = Storage.instance!!.getFavouritesStocks(query).subscribe {
                holder.adapter.addList(it)
            }
        }
        Search.instance.getQuery()
    }

    private fun initStocks(holder: FragmentViewHolder, item: Fragment) {
        holder.adapter = StocksAdapter()
        holder.recyclerView.adapter = holder.adapter
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
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}