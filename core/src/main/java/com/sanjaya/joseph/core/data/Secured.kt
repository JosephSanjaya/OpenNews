/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.data

object Secured {
    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
    external fun baseUrl(): String
}
