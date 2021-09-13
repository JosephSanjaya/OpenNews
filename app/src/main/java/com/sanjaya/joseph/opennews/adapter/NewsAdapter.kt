/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.adapter

import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.animation.SlideInLeftAnimation
import com.chad.library.adapter.base.module.LoadMoreModule
import com.sanjaya.joseph.core.domain.DefaultProvider
import com.sanjaya.joseph.core.domain.News
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.utils.buildEmptyAdapterView

class NewsAdapter(
    inflater: LayoutInflater,
    data: MutableList<DefaultProvider<News>>
) : BaseProviderMultiAdapter<DefaultProvider<News>>(data), LoadMoreModule {

    private val completeData = mutableListOf<News>()

    override fun getItemType(data: List<DefaultProvider<News>>, position: Int): Int {
        return when (val type = data[position]) {
            is DefaultProvider.Loading -> type.layoutType
            is DefaultProvider.Success -> type.layoutType
        }
    }

    fun startLoading(howMuch: Int) {
        completeData.clear()
        val tempData = mutableListOf<DefaultProvider<News>>()
        (0..howMuch).forEach { _ ->
            tempData.add(DefaultProvider.Loading())
        }
        setNewInstance(tempData)
    }

    private fun replaceOrAdd(updatedData: MutableList<News>) {
        when {
            updatedData.isNullOrEmpty() -> kotlin.runCatching {
                loadMoreModule.loadMoreEnd(true)
                loadMoreModule.loadMoreComplete()
            }
            else -> {
                updatedData.forEachIndexed { index, added ->
                    val indexData = data.indexOfFirst {
                        it is DefaultProvider.Success && it.data == added
                    }
                    if (indexData != -1) {
                        completeData[indexData] = added
                        setData(indexData, DefaultProvider.Success(added))
                    } else {
                        completeData.add(added)
                        addData(DefaultProvider.Success(added))
                    }
                    if (index == updatedData.lastIndex)
                        loadMoreModule.loadMoreComplete()
                }
            }
        }
    }

    fun updateData(updatedData: MutableList<News>) {
        if (completeData.isNullOrEmpty()) {
            completeData.addAll(updatedData)
            val newData = mutableListOf<DefaultProvider<News>>()
            newData.addAll(
                updatedData.map {
                    DefaultProvider.Success(it)
                }.toMutableList()
            )
            setNewInstance(newData)
        } else {
            replaceOrAdd(updatedData)
        }
    }

    fun filter(predicate: (News) -> Boolean) =
        setNewInstance(
            completeData.filter(predicate).map {
                DefaultProvider.Success(it)
            }.toMutableList()
        )

    init {
        adapterAnimation = SlideInLeftAnimation()
        animationEnable = true
        addChildClickViewIds(R.id.cvRoot)
        setEmptyView(
            inflater.buildEmptyAdapterView(
                title = "No News Data Found",
                msg = "Please check you keywords.",
                logo = R.drawable.bg_no_data
            )
        )
        loadMoreModule.apply {
            loadMoreModule.loadMoreView = LoadMoreView.news()
            loadMoreModule.checkDisableLoadMoreIfNotFullPage()
            isEnableLoadMore = true
        }
        addItemProvider(NewsProvider.Loading())
        addItemProvider(NewsProvider.Success())
    }
}
