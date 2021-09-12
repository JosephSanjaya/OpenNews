/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.data.api

import android.content.Context
import com.sanjaya.joseph.core.domain.News
import com.sanjaya.joseph.core.domain.ResponseResult
import com.sanjaya.joseph.core.domain.Sources
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaces {

    companion object {
        operator fun invoke(mContext: Context): ApiInterfaces {
            val baseService =
                ApiServices(
                    mContext
                )
            return baseService.retrofit.create(ApiInterfaces::class.java)
        }
    }

    /**
     * Get every news
     *
     * @param q: Keywords or phrases to search for in the article title and body.
     * @param qInTitl: Keywords or phrases to search for in the article title only.
     * @param sources:
     * A comma-seperated string of identifiers (maximum 20)
     * for the news sources or blogs you want headlines from.
     * @see Sources:
     * A comma-seperated string of domains
     * (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
     * @param excludeDomains: A comma-seperated string of domains
     * (eg bbc.co.uk, techcrunch.com, engadget.com) to remove from the results.
     * @param from: A date and optional time for the oldest article allowed.
     * This should be in ISO 8601 format (e.g. 2021-09-10 or 2021-09-10T18:11:28)
     * @param to: A date and optional time for the newest article allowed.
     * This should be in ISO 8601 format (e.g. 2021-09-10 or 2021-09-10T18:11:28)
     * @param language: The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * @see com.sanjaya.joseph.core.domain.Language
     * @param sortBy: The order to sort the articles in.
     * @see com.sanjaya.joseph.core.domain.Sort
     * @param pageSize: The number of results to return per page.
     * @param page: Use this to page through the results.
     */
    @GET("v2/everything")
    suspend fun getEveryNews(
        @Query("q") q: String? = null,
        @Query("qInTitle") qInTitle: String? = null,
        @Query("sources") sources: String? = null,
        @Query("excludeDomains") excludeDomains: String? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 0,
    ): ResponseResult<List<News>>

    /**
     * Get News sources
     *
     */
    @GET("v2/sources")
    suspend fun getSources(): ResponseResult<List<Sources>>

    /**
     * Get News Headlines sources
     *
     */
    @GET("v2/top-headlines/sources")
    suspend fun getTopHeadlinesSources(): ResponseResult<List<Sources>>

    /**
     * Get top headlines
     *
     * @param country: The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     * Note: you can't mix this param with the sources param.
     * @see com.sanjaya.joseph.core.domain.Country
     * @param category: The category you want to get headlines for.
     * Note: you can't mix this param with the sources param.
     * @see com.sanjaya.joseph.core.domain.Categories
     * @param sources: A comma-seperated string of identifiers for the news
     * sources or blogs you want headlines from.
     * @param q: Keywords or a phrase to search for.
     * @param pageSize: The number of results to return per page (request).
     * 20 is the default, 100 is the maximum.
     * @param page: Use this to page through the results if the total results
     * found is greater than the page size.
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") q: String? = null,
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 0,
    ): ResponseResult<List<News>>
}
