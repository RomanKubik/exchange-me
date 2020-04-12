package com.roman.kubik.exchangerates.domain.model

import com.roman.kubik.currency.Currency

data class ExchangeRates(val baseCurrency: Currency, val rates: Map<Currency, Double>)