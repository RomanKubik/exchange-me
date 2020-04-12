package com.roman.kubik.exchangerates.domain.model

import com.roman.kubik.currency.Currency

data class CurrencyRate(val baseCurrency: Currency, val currency: Currency, val exchangeRate: Double)