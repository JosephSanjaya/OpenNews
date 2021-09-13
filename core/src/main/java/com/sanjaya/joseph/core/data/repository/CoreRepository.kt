/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.data.repository

import com.sanjaya.joseph.core.data.api.ApiInterfaces
import com.sanjaya.joseph.core.domain.*
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.flow

class CoreRepository(
    private val service: ApiInterfaces,
) {

    /**
     * Get every news
     * @see ApiInterfaces.getEveryNews
     */
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
    ) = flow {
        emit(State.Double.Loading())
        val result = service.getEveryNews(
            q,
            qInTitle,
            sources?.id,
            excludeDomains,
            from?.toString(DateFormat.FORMAT_DATE),
            to?.toString(DateFormat.FORMAT_DATE),
            language.value,
            sortBy.value,
            pageSize,
            page
        )
        emit(State.Double.Success(result.totalResults, result.data))
    }

    /**
     * Get sources
     * @see ApiInterfaces.getSources
     */
    fun getSources() = flow {
        emit(State.Single.Loading())
        val result = service.getSources()
        emit(State.Single.Success(result.data))
    }

    /**
     * Get Top Headlines Sources
     * @see ApiInterfaces.getTopHeadlinesSources
     */
    fun getTopHeadlinesSources() = flow {
        emit(State.Single.Loading())
        val result = service.getTopHeadlinesSources()
        emit(State.Single.Success(result.sources))
    }

    /**
     * Get Top Headlines
     * @see ApiInterfaces.getTopHeadlines
     */
    fun getTopHeadlines(
        country: Country? = null,
        category: Categories? = null,
        sources: Sources? = null,
        q: String? = null,
        pageSize: Int = 20,
        page: Int = 0,
    ) = flow {
        emit(State.Double.Loading())
        val result = service.getTopHeadlines(
            country?.value,
            category?.value,
            sources?.id,
            q,
            pageSize,
            page
        )
        emit(State.Double.Success(result.totalResults, result.data))
    }
}
