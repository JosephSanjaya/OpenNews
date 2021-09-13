/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.adapter

import android.view.LayoutInflater
import com.blongho.country_data.World
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.animation.SlideInLeftAnimation
import com.chad.library.adapter.base.module.LoadMoreModule
import com.sanjaya.joseph.core.domain.DefaultProvider
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.utils.buildEmptyAdapterView

class SourceAdapter(
    inflater: LayoutInflater,
    data: MutableList<DefaultProvider<Sources>>
) : BaseProviderMultiAdapter<DefaultProvider<Sources>>(data), LoadMoreModule {

    private val completeData = mutableListOf<Sources>()

    override fun getItemType(data: List<DefaultProvider<Sources>>, position: Int): Int {
        return when (val type = data[position]) {
            is DefaultProvider.Loading -> type.layoutType
            is DefaultProvider.Success -> type.layoutType
        }
    }

    fun startLoading(howMuch: Int) {
        completeData.clear()
        val tempData = mutableListOf<DefaultProvider<Sources>>()
        (0..howMuch).forEach { _ ->
            tempData.add(DefaultProvider.Loading())
        }
        setNewInstance(tempData)
    }

    fun updateData(updatedData: MutableList<Sources>) {
        completeData.clear()
        completeData.addAll(updatedData)
        val newData = mutableListOf<DefaultProvider<Sources>>()
        newData.addAll(
            updatedData.map {
                DefaultProvider.Success(
                    it.apply {
                        flag = World.getFlagOf(it.country)
                    }
                )
            }.toMutableList()
        )
        setNewInstance(newData)
    }

    fun filter(predicate: (Sources) -> Boolean) =
        setNewInstance(
            completeData.filter(predicate).map {
                DefaultProvider.Success(
                    it.apply {
                        flag = World.getFlagOf(it.country)
                    }
                )
            }.toMutableList()
        )

    init {
        adapterAnimation = SlideInLeftAnimation()
        animationEnable = true
        addChildClickViewIds(R.id.cvRoot)
        setEmptyView(
            inflater.buildEmptyAdapterView(
                title = "No Source Data Found",
                msg = "Please check you keywords.",
                logo = R.drawable.bg_no_data
            )
        )
        loadMoreModule.apply {
            loadMoreModule.loadMoreView = LoadMoreView.sources()
            loadMoreModule.checkDisableLoadMoreIfNotFullPage()
            isEnableLoadMore = true
        }
        addItemProvider(SourcesProvider.Loading())
        addItemProvider(SourcesProvider.Success())
    }
}
