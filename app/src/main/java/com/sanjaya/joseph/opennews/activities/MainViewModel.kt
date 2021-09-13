/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.activities

import com.sanjaya.joseph.core.domain.Categories
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.core.presentation.BaseViewModel

class MainViewModel : BaseViewModel() {
    private var _categories = Categories.General
    val categories get() = _categories
    fun setCategories(categories: Categories) {
        _categories = categories
    }

    private var _sources: Sources? = null
    val sources get() = _sources
    fun setSources(sources: Sources) {
        _sources = sources
    }
}
