/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sanjaya.joseph.core.domain.DefaultProvider
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.core.presentation.core.CoreObserver
import com.sanjaya.joseph.core.presentation.core.CoreViewModel
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.activities.MainViewModel
import com.sanjaya.joseph.opennews.adapter.SourceAdapter
import com.sanjaya.joseph.opennews.databinding.FragmentSourcesBinding
import com.sanjaya.joseph.opennews.utils.prepareAnim
import org.koin.androidx.viewmodel.ext.android.viewModel

class SourcesFragment :
    Fragment(R.layout.fragment_sources),
    SwipeRefreshLayout.OnRefreshListener,
    OnItemClickListener,
    CoreObserver.Interfaces {
    private val mBinding by viewBinding(FragmentSourcesBinding::bind)
    private val mData = mutableListOf<DefaultProvider<Sources>>()
    private val mAdapter by lazy {
        SourceAdapter(layoutInflater, mData).apply {
            setOnItemClickListener(this@SourcesFragment)
        }
    }
    private val mActivityViewModel by activityViewModels<MainViewModel>()
    private val mViewModel by viewModel<CoreViewModel>()
    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLifecycleOwner.lifecycle.addObserver(
            CoreObserver(this, mViewModel, viewLifecycleOwner)
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.srlRefresh.setOnRefreshListener(this)
        mBinding.etSearch.addTextChangedListener { s ->
            mHandler.removeCallbacksAndMessages(null)
            mHandler.postDelayed(
                {
                    mAdapter.filter { it.toString().contains(s.toString(), ignoreCase = true) }
                },
                700
            )
        }
        mBinding.rvCategories.adapter = mAdapter
        onRefresh()
    }

    override fun onRefresh() {
        mViewModel.getTopHeadlineSources(mActivityViewModel.categories)
    }

    override fun onHeadlinesSourcesLoading() {
        super.onHeadlinesSourcesLoading()
        mBinding.srlRefresh.prepareAnim()
        mAdapter.startLoading(SKELETON_COUNT)
        mBinding.srlRefresh.isRefreshing = true
    }

    override fun onHeadlinesSourcesSuccess(sources: List<Sources>?) {
        super.onHeadlinesSourcesSuccess(sources)
        mAdapter.updateData((sources ?: emptyList()).toMutableList())
        mBinding.srlRefresh.isRefreshing = false
    }

    override fun onHeadlinesSourcesFailed(e: Throwable) {
        super.onHeadlinesSourcesFailed(e)
        mBinding.srlRefresh.isRefreshing = false
        mAdapter.setNewInstance(mutableListOf())
        ToastUtils.showShort(e.message ?: "Failed to load data.")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val selected = mAdapter.getItem(position)
        if (selected is DefaultProvider.Success) {
            mActivityViewModel.setSources(selected.data)
            findNavController().navigate(R.id.action_sourceFragment_to_newsFragment)
        }
    }

    companion object {
        const val SKELETON_COUNT = 10
    }
}
