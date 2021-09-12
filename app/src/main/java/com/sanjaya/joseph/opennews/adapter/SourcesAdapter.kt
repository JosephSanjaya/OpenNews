/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.adapter

import coil.load
import com.blongho.country_data.World
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.SlideInBottomAnimation
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.RowCategoryBinding
import java.util.*

class SourcesAdapter :
    BaseQuickAdapter<Pair<Int, Sources>, BaseDataBindingHolder<RowCategoryBinding>>(
        R.layout.row_category
    ) {

    private val completeData = mutableListOf<Pair<Int, Sources>>()

    fun updateData(newData: List<Sources>) {
        val sda = newData.map {
            Pair(World.getFlagOf(it.country), it)
        }.toMutableList()
        setNewInstance(sda)
    }

    override fun convert(
        holder: BaseDataBindingHolder<RowCategoryBinding>,
        item: Pair<Int, Sources>
    ) {
        holder.dataBinding?.apply {
            ivBG.load(item.first) {
                crossfade(true)
                placeholder(R.drawable.ic_loading_anim)
            }
            tvTitle.text = item.second.name?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }
    }

    init {
        animationEnable = true
        adapterAnimation = SlideInBottomAnimation()
        addChildClickViewIds(R.id.cvRoot)
    }
}
