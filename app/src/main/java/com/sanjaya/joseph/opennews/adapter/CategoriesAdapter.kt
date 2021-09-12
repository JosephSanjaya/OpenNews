/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.adapter

import coil.load
import coil.transition.CrossfadeTransition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.animation.SlideInBottomAnimation
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.sanjaya.joseph.core.domain.Categories
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.RowCategoryBinding
import java.util.*

class CategoriesAdapter :
    BaseQuickAdapter<Map.Entry<String, Categories>, BaseDataBindingHolder<RowCategoryBinding>>(
        R.layout.row_category
    ) {

    fun initCategories() {

        setNewInstance(
            Categories.values.map {
                it
            }.toMutableList()
        )
    }

    override fun convert(
        holder: BaseDataBindingHolder<RowCategoryBinding>,
        item: Map.Entry<String, Categories>
    ) {
        holder.dataBinding?.apply {
            ivBG.load("https://loremflickr.com/320/100/${item.value.value}") {
                crossfade(true)
                placeholder(R.drawable.ic_loading_anim)
            }
            tvTitle.text = item.value.value.replaceFirstChar {
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
