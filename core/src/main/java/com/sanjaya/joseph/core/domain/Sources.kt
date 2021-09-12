/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

import com.squareup.moshi.Json
import com.thinkinglogic.builder.annotation.Builder

data class Sources(
    val flag: Int? = null,

    @Json(name = "country")
    val country: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "language")
    val language: String? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "category")
    val category: String? = null,

    @Json(name = "url")
    val url: String? = null
)
