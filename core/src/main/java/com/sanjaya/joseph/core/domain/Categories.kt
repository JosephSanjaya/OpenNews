/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

enum class Categories(val value: String) {
    Business("business"),
    General("general"),
    Health("health"),
    Science("science"),
    Sports("sports"),
    Technology("technology"),
    Entertainment("entertainment");

    companion object {
        val values by lazy {
            values().associateBy(Categories::value)
        }

        fun fromString(value: String) = values[value]
    }
}
