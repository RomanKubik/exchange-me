package com.roman.kubik.exchangerates.domain.usecase

import com.roman.kubik.core.model.Result
import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesUseCase {

    fun changeResponder(currency: CurrencyRate, amount: Double)
    fun getRates(currency: Currency, amount: Double): Flow<Result<List<CurrencyRate>>>
}