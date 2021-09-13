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
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.RowCategoryBinding
import com.sanjaya.joseph.opennews.databinding.RowSourcesLoadingBinding

object SourcesProvider {
    class Loading(
        override val itemViewType: Int = DefaultProvider.LOADING_LAYOUT,
        override val layoutId: Int = R.layout.row_sources_loading,
    ) : BaseItemProvider<DefaultProvider<Sources>>() {
        override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
            DataBindingUtil.bind<RowSourcesLoadingBinding>(viewHolder.itemView)
            super.onViewHolderCreated(viewHolder, viewType)
        }

        override fun convert(helper: BaseViewHolder, item: DefaultProvider<Sources>) {
            DataBindingUtil.getBinding<RowSourcesLoadingBinding>(helper.itemView)
                ?.loadingView
                ?.startShimmer()
        }
    }

    class Success(
        override val itemViewType: Int = DefaultProvider.SUCCESS_LAYOUT,
        override val layoutId: Int = R.layout.row_category,
    ) : BaseItemProvider<DefaultProvider<Sources>>() {

        override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
            DataBindingUtil.bind<RowCategoryBinding>(viewHolder.itemView)
            super.onViewHolderCreated(viewHolder, viewType)
        }

        override fun convert(helper: BaseViewHolder, item: DefaultProvider<Sources>) {
            item as DefaultProvider.Success
            DataBindingUtil.getBinding<RowCategoryBinding>(helper.itemView)?.apply {
                tvTitle.text = item.data.name
                item.data.flag?.let {
                    ivBG.load(it)
                }
            }
        }
    }
}
