package com.roman.kubik.core.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.roman.kubik.core.R
import java.text.DecimalFormat


object CurrencyUtils {

    var decimalFormat: DecimalFormat = DecimalFormat("##0.00")


    private val currencyFlags = hashMapOf(
        "EUR" to R.drawable.flag_european_union,
        "AUD" to R.drawable.flag_australia,
        "BGN" to R.drawable.flag_bulgaria,
        "BRL" to R.drawable.flag_brazil,
        "CAD" to R.drawable.flag_canada,
        "CHF" to R.drawable.flag_switzerland,
        "CNY" to R.drawable.flag_china,
        "CZK" to R.drawable.flag_czech_republic,
        "DKK" to R.drawable.flag_denmark,
        "GBP" to R.drawable.flag_england,
        "HKD" to R.drawable.flag_hong_kong,
        "HRK" to R.drawable.flag_croatia,
        "HUF" to R.drawable.flag_hungary,
        "IDR" to R.drawable.flag_indonesia,
        "ILS" to R.drawable.flag_israel,
        "INR" to R.drawable.flag_india,
        "ISK" to R.drawable.flag_iceland,
        "JPY" to R.drawable.flag_japan,
        "KRW" to R.drawable.flag_south_korea,
        "MXN" to R.drawable.flag_mexico,
        "MYR" to R.drawable.flag_malaysia,
        "NOK" to R.drawable.flag_norway,
        "NZD" to R.drawable.flag_new_zealand,
        "PHP" to R.drawable.flag_philippines,
        "PLN" to R.drawable.flag_republic_of_poland,
        "RON" to R.drawable.flag_romania,
        "RUB" to R.drawable.flag_russia,
        "SEK" to R.drawable.flag_sweden,
        "SGD" to R.drawable.flag_singapore,
        "THB" to R.drawable.flag_thailand,
        "USD" to R.drawable.flag_usa,
        "ZAR" to R.drawable.flag_south_africa
    )

    private val currencyNames = hashMapOf(
        "EUR" to R.string.currency_EUR,
        "AUD" to R.string.currency_AUD,
        "BGN" to R.string.currency_BGN,
        "BRL" to R.string.currency_BRL,
        "CAD" to R.string.currency_CAD,
        "CHF" to R.string.currency_CHF,
        "CNY" to R.string.currency_CNY,
        "CZK" to R.string.currency_CZK,
        "DKK" to R.string.currency_DKK,
        "GBP" to R.string.currency_GBP,
        "HKD" to R.string.currency_HKD,
        "HRK" to R.string.currency_HRK,
        "HUF" to R.string.currency_HUF,
        "IDR" to R.string.currency_IDR,
        "ILS" to R.string.currency_ILS,
        "INR" to R.string.currency_INR,
        "ISK" to R.string.currency_ISK,
        "JPY" to R.string.currency_JPY,
        "KRW" to R.string.currency_KRW,
        "MXN" to R.string.currency_MXN,
        "MYR" to R.string.currency_MYR,
        "NOK" to R.string.currency_NOK,
        "NZD" to R.string.currency_NZD,
        "PHP" to R.string.currency_PHP,
        "PLN" to R.string.currency_PLN,
        "RON" to R.string.currency_RON,
        "RUB" to R.string.currency_RUB,
        "SEK" to R.string.currency_SEK,
        "SGD" to R.string.currency_SGD,
        "THB" to R.string.currency_THB,
        "USD" to R.string.currency_USD,
        "ZAR" to R.string.currency_ZAR
    )

    fun formatDecimal(decimal: Double): String {
        return decimalFormat.format(decimal)
    }

    @DrawableRes
    fun getCurrencyFlag(currencyCode: String): Int {
        return currencyFlags[currencyCode] ?: R.drawable.flag_default
    }

    @StringRes
    fun getCurrencyName(currencyCode: String): Int {
        return currencyNames[currencyCode] ?: R.string.currency_unknown
    }

}