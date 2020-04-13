package com.roman.kubik.exchangerates.domain.usecase

import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.model.CurrencyRatesResult
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface ExchangeRatesUseCase {

    fun changeResponder(currency: CurrencyRate, amount: BigDecimal)
    fun getRates(currency: Currency, amount: BigDecimal): Flow<CurrencyRatesResult>
}