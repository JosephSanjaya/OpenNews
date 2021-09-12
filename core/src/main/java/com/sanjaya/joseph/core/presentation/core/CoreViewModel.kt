/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.presentation.core

import com.sanjaya.joseph.core.data.repository.CoreRepository
import com.sanjaya.joseph.core.domain.*
import com.sanjaya.joseph.core.presentation.BaseViewModel
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoreViewModel(
    private val repo: CoreRepository
) : BaseViewModel() {

    private val _everyNewsState =
        MutableStateFlow<State.Double<Int?, List<News>?>>(State.Double.Idle())
    val mEveryNewsState get() = _everyNewsState.asImmutable()

    fun resetEveryNewsState() {
        _everyNewsState.resetDoubleState()
    }

    fun getEveryNews(
        q: String? = null,
        qInTitle: String? = null,
        sources: Sources? = null,
        excludeDomains: String? = null,
        from: DateTime? = null,
        to: DateTime? = null,
        language: Language = Language.EN,
        sortBy: Sort = Sort.PublishedAt,
        pageSize: Int = 20,
        page: Int = 0,
    ) = ioScope.launch {
        repo.getEveryNews(
            q,
            qInTitle,
            sources,
            excludeDomains,
            from,
            to,
            language,
            sortBy,
            pageSize,
            page
        )
            .catch {
                _everyNewsState.emit(State.Double.Failed(it))
            }
            .collect {
                _everyNewsState.emit(it)
            }
    }

    private val _sources =
        MutableStateFlow<State.Single<List<Sources>?>>(State.Single.Idle())
    val mSources get() = _sources.asImmutable()

    fun resetSourcesState() {
        _sources.resetSingleState()
    }

    fun getSources() = ioScope.launch {
        repo.getSources()
            .catch {
                _sources.emit(State.Single.Failed(it))
            }
            .collect {
                _sources.emit(it)
            }
    }

    private val _headlineSources =
        MutableStateFlow<State.Single<List<Sources>?>>(State.Single.Idle())
    val mHeadlineSources get() = _sources.asImmutable()

    fun resetHeadlineSources() {
        _headlineSources.resetSingleState()
    }

    fun getTopHeadlineSources() = ioScope.launch {
        repo.getSources()
            .catch {
                _headlineSources.emit(State.Single.Failed(it))
            }
            .collect {
                _headlineSources.emit(it)
            }
    }

    private val _headlines =
        MutableStateFlow<State.Double<Int?, List<News>?>>(State.Double.Idle())
    val mHeadlines get() = _headlines.asImmutable()

    fun resetHeadlinesState() {
        _headlines.resetDoubleState()
    }

    fun getTopHeadlines(
        country: Country? = null,
        category: Categories? = null,
        sources: Sources? = null,
        q: String? = null,
        pageSize: Int = 20,
        page: Int = 0,
    ) = ioScope.launch {
        repo.getTopHeadlines(country, category, sources, q, pageSize, page)
            .catch {
                _headlines.emit(State.Double.Failed(it))
            }
            .collect {
                _headlines.emit(it)
            }
    }
}
