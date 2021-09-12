/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

sealed class DefaultProvider<T> {
    companion object {
        const val LOADING_LAYOUT = 0
        const val SUCCESS_LAYOUT = 1
    }

    data class Loading<T>(val layoutType: Int = LOADING_LAYOUT) : DefaultProvider<T>()
    data class Success<T>(val data: T, val layoutType: Int = SUCCESS_LAYOUT) : DefaultProvider<T>()
}
