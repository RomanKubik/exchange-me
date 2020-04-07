package com.roman.kubik.exchangerates.domain.repository

import com.roman.kubik.core.model.Result
import com.roman.kubik.exchangerates.domain.model.ExchangeRates

interface ExchangeRatesRepository {

    suspend fun getLatestExchangeRates(currencyCode: String): Result<ExchangeRates>
}