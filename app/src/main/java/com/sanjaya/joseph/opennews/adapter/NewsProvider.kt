/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.adapter

import androidx.databinding.DataBindingUtil
import coil.load
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.sanjaya.joseph.core.domain.DefaultProvider
import com.sanjaya.joseph.core.domain.News
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.RowNewsBinding
import com.sanjaya.joseph.opennews.databinding.RowNewsLoadingBinding

object NewsProvider {
    class Loading(
        override val itemViewType: Int = DefaultProvider.LOADING_LAYOUT,
        override val layoutId: Int = R.layout.row_news_loading,
    ) : BaseItemProvider<DefaultProvider<News>>() {
        override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
            DataBindingUtil.bind<RowNewsLoadingBinding>(viewHolder.itemView)
            super.onViewHolderCreated(viewHolder, viewType)
        }

        override fun convert(helper: BaseViewHolder, item: DefaultProvider<News>) {
            DataBindingUtil.getBinding<RowNewsLoadingBinding>(helper.itemView)
                ?.loadingView
                ?.startShimmer()
        }
    }

    class Success(
        override val itemViewType: Int = DefaultProvider.SUCCESS_LAYOUT,
        override val layoutId: Int = R.layout.row_news,
    ) : BaseItemProvider<DefaultProvider<News>>() {

        override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
            DataBindingUtil.bind<RowNewsBinding>(viewHolder.itemView)
            super.onViewHolderCreated(viewHolder, viewType)
        }

        override fun convert(helper: BaseViewHolder, item: DefaultProvider<News>) {
            item as DefaultProvider.Success
            DataBindingUtil.getBinding<RowNewsBinding>(helper.itemView)?.apply {
                tvTitle.text = item.data.title
                ivImage.load(item.data.urlToImage)
                tvDate.text = item.data.publishedAt
            }
        }
    }
}
