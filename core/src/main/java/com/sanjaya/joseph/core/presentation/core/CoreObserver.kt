/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.presentation.core

import androidx.lifecycle.*
import com.sanjaya.joseph.core.domain.News
import com.sanjaya.joseph.core.domain.Sources
import com.sanjaya.joseph.core.domain.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoreObserver(
    private val view: Interfaces,
    private val viewModel: CoreViewModel,
    private val owner: LifecycleOwner
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setupObserver() {
        observeNews()
        observerSources()
    }

    private fun observeNews() {
        owner.lifecycleScope.launch {
            viewModel.mEveryNewsState.collect {
                when (it) {
                    is State.Double.Idle -> view.onEveryNewsIdle()
                    is State.Double.Loading -> view.onEveryNewsLoading()
                    is State.Double.Success -> {
                        view.onEveryNewsSuccess(it.data1, it.data2)
                        viewModel.resetEveryNewsState()
                    }
                    is State.Double.Failed -> {
                        view.onEveryNewsFailed(it.throwable)
                        viewModel.resetEveryNewsState()
                    }
                }
            }
        }
        owner.lifecycleScope.launch {
            viewModel.mHeadlines.collect {
                when (it) {
                    is State.Double.Idle -> view.onTopHeadlinesIdle()
                    is State.Double.Loading -> view.onTopHeadlinesLoading()
                    is State.Double.Success -> {
                        view.onTopHeadlinesSuccess(it.data1, it.data2)
                        viewModel.resetHeadlinesState()
                    }
                    is State.Double.Failed -> {
                        view.onTopHeadlinesFailed(it.throwable)
                        viewModel.resetHeadlinesState()
                    }
                }
            }
        }
    }

    private fun observerSources() {
        owner.lifecycleScope.launch {
            viewModel.mSources.collect {
                when (it) {
                    is State.Single.Idle -> view.onSourcesIdle()
                    is State.Single.Loading -> view.onSourcesLoading()
                    is State.Single.Success -> {
                        view.onSourcesSuccess(it.data)
                        viewModel.resetSourcesState()
                    }
                    is State.Single.Failed -> {
                        view.onSourcesFailed(it.throwable)
                        viewModel.resetSourcesState()
                    }
                }
            }
        }
        owner.lifecycleScope.launch {
            viewModel.mHeadlineSources.collect {
                when (it) {
                    is State.Single.Idle -> view.onHeadlinesSourcesIdle()
                    is State.Single.Loading -> view.onHeadlinesSourcesLoading()
                    is State.Single.Success -> {
                        view.onHeadlinesSourcesSuccess(it.data)
                        viewModel.resetHeadlineSources()
                    }
                    is State.Single.Failed -> {
                        view.onHeadlinesSourcesFailed(it.throwable)
                        viewModel.resetHeadlineSources()
                    }
                }
            }
        }
    }

    interface Interfaces {
        fun onEveryNewsIdle() {}
        fun onEveryNewsLoading() {}
        fun onEveryNewsSuccess(resultCount: Int?, news: List<News>?) {}
        fun onEveryNewsFailed(e: Throwable) {}

        fun onTopHeadlinesIdle() {}
        fun onTopHeadlinesLoading() {}
        fun onTopHeadlinesSuccess(resultCount: Int?, news: List<News>?) {}
        fun onTopHeadlinesFailed(e: Throwable) {}

        fun onSourcesIdle() {}
        fun onSourcesLoading() {}
        fun onSourcesSuccess(sources: List<Sources>?) {}
        fun onSourcesFailed(e: Throwable) {}

        fun onHeadlinesSourcesIdle() {}
        fun onHeadlinesSourcesLoading() {}
        fun onHeadlinesSourcesSuccess(sources: List<Sources>?) {}
        fun onHeadlinesSourcesFailed(e: Throwable) {}
    }
}
