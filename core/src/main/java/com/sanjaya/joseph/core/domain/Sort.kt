/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

enum class Sort(val value: String) {
    /**
     * Articles more closely related to q come first.
     */
    Relevancy("relevancy"),

    /**
     * Articles from popular sources and publishers come first.
     */
    Popularity("popularity"),

    /**
     * Newest articles come first.
     */
    PublishedAt("publishedAt");

    companion object {
        private val map =
            values().associateBy(Sort::value)

        fun fromString(value: String) = map[value]
    }
}
