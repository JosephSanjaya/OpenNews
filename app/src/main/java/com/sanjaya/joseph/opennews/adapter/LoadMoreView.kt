/**
 * @Author Joseph Sanjaya on 13,May,2020,
 * @Company (PT. Solusi Finansialku Indonesia),
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/)
 */

package com.sanjaya.joseph.opennews.adapter

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sanjaya.joseph.opennews.R

class LoadMoreView(
    private val type: Type = Type.Sources()
) : BaseLoadMoreView() {

    override fun getRootView(parent: ViewGroup): View =
        parent.getItemView(
            when (type) {
                is Type.Sources -> type.layout
                is Type.News -> type.layout
            }
        )

    override fun getLoadingView(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_loading_view)

    override fun getLoadComplete(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_load_complete_view)

    override fun getLoadEndView(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_load_end_view)

    override fun getLoadFailView(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_load_fail_view)

    companion object {
        sealed class Type {
            class Sources(val layout: Int = R.layout.row_sources_loading) : Type()
            class News(val layout: Int = R.layout.row_news_loading) : Type()
        }

        fun sources(): LoadMoreView = LoadMoreView(Type.Sources())
        fun news(): LoadMoreView = LoadMoreView(Type.News())
    }
}
