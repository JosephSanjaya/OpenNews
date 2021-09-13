/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.data.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sanjaya.joseph.core.BuildConfig
import com.sanjaya.joseph.core.data.Secured
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServices(context: Context) {
    private val chuckerCollector = ChuckerCollector(
        context = context,
        showNotification = BuildConfig.DEBUG,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )
    private val chuckerInterceptor = ChuckerInterceptor.Builder(context).apply {
        collector(chuckerCollector)
        maxContentLength(MAX_CONTENT_LENGTH)
        redactHeaders("Authorization")
        alwaysReadResponseBody(true)
    }.build()

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .addInterceptor { chain ->
                    chain.request().let { original ->
                        val httpUrl = original.url
                        val newHttpUrl = httpUrl.newBuilder()
                            .build()
                        val requestBuilder = original.newBuilder().url(newHttpUrl)
                            .addHeader("Authorization", Secured.apiKey())
                            .addHeader("Content-Type", "application/json")
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                }
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor(chuckerInterceptor)
                .build()
        }
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Secured.baseUrl())
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        const val MAX_CONTENT_LENGTH = 250_000L
    }
}
