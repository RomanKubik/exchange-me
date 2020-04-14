package com.roman.kubik.exchangerates.domain.model

import java.lang.Exception

data class CurrencyRatesResult(val data: List<CurrencyRate>, val exception: Exception? = null)