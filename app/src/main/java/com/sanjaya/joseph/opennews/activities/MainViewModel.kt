/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.activities

import androidx.lifecycle.MutableLiveData
import com.sanjaya.joseph.core.domain.Categories
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.core.presentation.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val _categories = MutableLiveData(Categories.General)
    val mCategories get() = _categories.asImmutable()
    fun setCategories(categories: Categories) {
        _categories.postValue(categories)
    }

    private val _sources = MutableLiveData<Sources>()
    val mSources get() = _sources.asImmutable()
    fun setSources(sources: Sources) {
        _sources.postValue(sources)
    }

    fun getCategoriesAndSources() = Pair(_categories.value, _sources.value)
}
