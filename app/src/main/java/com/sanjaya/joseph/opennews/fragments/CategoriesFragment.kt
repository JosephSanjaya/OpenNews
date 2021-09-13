/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.activities.MainActivity.Companion.launchSources
import com.sanjaya.joseph.opennews.activities.MainViewModel
import com.sanjaya.joseph.opennews.adapter.CategoriesAdapter
import com.sanjaya.joseph.opennews.databinding.FragmentCategoriesBinding
import com.sanjaya.joseph.opennews.utils.prepareAnim

class CategoriesFragment :
    Fragment(R.layout.fragment_categories),
    SwipeRefreshLayout.OnRefreshListener,
    OnItemClickListener {
    private val mBinding by viewBinding(FragmentCategoriesBinding::bind)
    private val mAdapter by lazy {
        CategoriesAdapter().apply {
            setOnItemClickListener(this@CategoriesFragment)
        }
    }
    private val mSharedViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.srlRefresh.setOnRefreshListener(this)
        mBinding.rvCategories.adapter = mAdapter
        onRefresh()
    }

    override fun onRefresh() {
        mBinding.srlRefresh.prepareAnim()
        mAdapter.initCategories()
        mBinding.srlRefresh.isRefreshing = false
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val selected = mAdapter.getItem(position)
        mSharedViewModel.setCategories(selected.value)
        context?.launchSources()
    }
}
