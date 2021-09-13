/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

import com.google.gson.annotations.SerializedName

data class ResponseResult<T>(
    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("status")
    val message: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: Int? = null
)
