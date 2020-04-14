package com.roman.kubik.exchangerates.domain.model

import com.roman.kubik.currency.Currency
import java.math.BigDecimal

data class CurrencyRate(val baseCurrency: Currency, val currency: Currency, val exchangeRate: BigDecimal)