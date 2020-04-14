package com.roman.kubik.exchangerates.ui

import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import java.math.BigDecimal

interface ExchangeItemCallback {

    fun onResponderChanged(currencyRate: CurrencyRate)

    fun onAmountEdited(currencyRate: CurrencyRate, amount: BigDecimal)
}