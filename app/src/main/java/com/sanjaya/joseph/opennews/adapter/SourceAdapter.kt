/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.animation.SlideInLeftAnimation
import com.sanjaya.joseph.core.domain.DefaultProvider
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.utils.buildEmptyAdapterView

class SourceAdapter(
    inflater: LayoutInflater,
    data: MutableList<DefaultProvider<Sources>>
) : BaseProviderMultiAdapter<DefaultProvider<Sources>>(data) {

    override fun getItemType(data: List<DefaultProvider<Sources>>, position: Int): Int {
        return when (val type = data[position]) {
            is DefaultProvider.Loading -> type.layoutType
            is DefaultProvider.Success -> type.layoutType
        }
    }

    fun startLoading(howMuch: Int) {
        val tempData = mutableListOf<DefaultProvider<Sources>>()
        (0..howMuch).forEach { _ ->
            tempData.add(DefaultProvider.Loading())
        }
        setNewInstance(tempData)
    }


    init {
        adapterAnimation = SlideInLeftAnimation()
        animationEnable = true
        addChildClickViewIds(R.id.cvRoot)
        setEmptyView(
            inflater.buildEmptyAdapterView(
                title = "Belum membuat Catatan Keuangan",
                msg = "Tekan Tambah untuk membuat Catatan Keuangan",
                logo = R.drawable.ic_illustrasi_empty
            )
        )
        loadMoreModule.apply {
            loadMoreModule.loadMoreView = LoadMoreView.transaksi()
            loadMoreModule.checkDisableLoadMoreIfNotFullPage()
            isEnableLoadMore = true
        }
        addItemProvider(TransaksiProvider.Loading())
        addItemProvider(TransaksiProvider.Overview(interfaces = interfaces))
        addItemProvider(TransaksiProvider.Data(interfaces = interfaces, mRekeningId = rekeningId))
    }
}
