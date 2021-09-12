/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

import com.squareup.moshi.Json

data class ResponseResult<T>(
    @Json(name = "data")
    val data: T? = null,

    @Json(name = "status")
    val message: String? = null,

    @Json(name = "totalResults")
    val totalResults: Int? = null
)
