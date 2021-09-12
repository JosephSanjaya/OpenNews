/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

enum class Language(val value: String) {
    AR("ar"),
    DE("de"),
    EN("en"),
    ES("es"),
    FR("fr"),
    HE("he"),
    IT("it"),
    HL("nl"),
    NO("no"),
    PT("pt"),
    RU("ru"),
    SE("se"),
    UD("ud"),
    ZH("zh");

    companion object {
        private val map =
            values().associateBy(Language::value)

        fun fromString(value: String) = map[value]
    }
}
