package com.roman.kubik.exchangerates.domain.model

import com.roman.kubik.currency.Currency
import java.math.BigDecimal

data class ExchangeRates(val baseCurrency: Currency, val rates: Map<Currency, BigDecimal>)