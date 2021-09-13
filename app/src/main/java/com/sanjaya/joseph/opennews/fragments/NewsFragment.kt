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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.sanjaya.joseph.core.domain.DefaultProvider
import com.sanjaya.joseph.core.domain.News
import com.sanjaya.joseph.core.presentation.core.CoreObserver
import com.sanjaya.joseph.core.presentation.core.CoreViewModel
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.activities.MainViewModel
import com.sanjaya.joseph.opennews.adapter.NewsAdapter
import com.sanjaya.joseph.opennews.databinding.FragmentNewsBinding
import com.sanjaya.joseph.opennews.utils.prepareAnim
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.net.Uri


class NewsFragment :
    Fragment(R.layout.fragment_news),
    SwipeRefreshLayout.OnRefreshListener,
    OnItemClickListener,
    OnLoadMoreListener,
    CoreObserver.Interfaces {
    private val mActivityViewModel by activityViewModels<MainViewModel>()
    private val mBinding by viewBinding(FragmentNewsBinding::bind)
    private val mData = mutableListOf<DefaultProvider<News>>()
    private val mAdapter by lazy {
        NewsAdapter(layoutInflater, mData).apply {
            setOnItemClickListener(this@NewsFragment)
            loadMoreModule.setOnLoadMoreListener(this@NewsFragment)
        }
    }
    private var mQuery = ""
    private var mLimit = 10
    private var mOffset = 0
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
                    mQuery = s.toString()
                    onRefresh()
                },
                700
            )
        }
        mBinding.rvNews.adapter = mAdapter
        onRefresh()
    }

    override fun onRefresh() {
        mAdapter.setNewInstance(mutableListOf())
        mOffset = 0
        requestData(mQuery)
    }

    private fun requestData(q: String? = null) {
        mViewModel.getTopHeadlines(
            sources = mActivityViewModel.sources,
            pageSize = mLimit,
            q = q,
            page = mOffset
        )
    }

    override fun onTopHeadlinesLoading() {
        super.onTopHeadlinesLoading()
        mBinding.srlRefresh.prepareAnim()
        if (mAdapter.data.isEmpty()) {
            mAdapter.startLoading(SKELETON_COUNT)
            mBinding.srlRefresh.isRefreshing = true
        }
    }

    override fun onTopHeadlinesSuccess(resultCount: Int?, news: List<News>?) {
        super.onTopHeadlinesSuccess(resultCount, news)
        mAdapter.updateData((news ?: emptyList()).toMutableList())
        mBinding.srlRefresh.isRefreshing = false
    }

    override fun onTopHeadlinesFailed(e: Throwable) {
        super.onTopHeadlinesFailed(e)
        mBinding.srlRefresh.isRefreshing = false
        mAdapter.setNewInstance(mutableListOf())
        ToastUtils.showShort(e.message ?: "Failed to load data.")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val selected = mAdapter.getItem(position)
        if (selected is DefaultProvider.Success) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(selected.data.url)
            startActivity(i)
        }
    }

    override fun onLoadMore() {
        mBinding.rvNews.postDelayed(
            {
                mOffset += 1
                requestData(mQuery)
            },
            100
        )
    }

    companion object {
        const val SKELETON_COUNT = 10
    }
}
