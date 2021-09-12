package com.sanjaya.joseph.core.utils

import com.squareup.moshi.Moshi

inline fun <reified T> T.toJSON(): String =
    Moshi.Builder().build()
        .adapter(T::class.java)
        .toJson(this)

inline fun <reified T> String.toObject(): T? =
    Moshi.Builder().build()
        .adapter(T::class.java)
        .fromJson(this)
