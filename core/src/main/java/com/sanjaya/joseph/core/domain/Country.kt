/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.domain

enum class Country(val value: String) {
    AE("ae"),
    AR("ar"),
    AT("at"),
    AU("au"),
    BE("be"),
    BG("bg"),
    BR("br"),
    CA("ca"),
    CH("ch"),
    CN("cn"),
    CO("co"),
    CU("cu"),
    CZ("cz"),
    DE("de"),
    EG("eg"),
    FR("fr"),
    GB("gb"),
    GR("gr"),
    HK("hk"),
    HU("hu"),
    ID("id"),
    IE("ie"),
    IL("il"),
    IN("in"),
    IT("it"),
    JP("jp"),
    KR("kr"),
    LT("lt"),
    LV("lv"),
    MA("ma"),
    MX("mx"),
    MY("my"),
    NG("ng"),
    NL("nl"),
    NO("no"),
    NZ("nz"),
    PH("ph"),
    PL("pl"),
    PT("pt"),
    RO("ro"),
    RS("rs"),
    RU("ru"),
    SA("sa"),
    SE("se"),
    SG("sg"),
    SI("si"),
    SK("sk"),
    TH("th"),
    TR("tr"),
    TW("tw"),
    UA("ua"),
    US("us"),
    VE("ve"),
    ZA("za");

    companion object {
        private val map =
            values().associateBy(Country::value)

        fun fromString(value: String) = map[value]
    }
}
