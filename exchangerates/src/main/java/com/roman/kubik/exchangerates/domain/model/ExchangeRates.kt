package com.roman.kubik.exchangerates.domain.model

data class ExchangeRates(val baseCurrency: String, val rates: Map<String, Double>)