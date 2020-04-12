package com.roman.kubik.exchangerates.ui

import com.roman.kubik.exchangerates.domain.model.CurrencyRate

interface ExchangeItemCallback {

    fun onResponderChanged(currencyRate: CurrencyRate)

    fun onAmountEdited(currencyRate: CurrencyRate, amount: Double)
}