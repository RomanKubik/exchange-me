package com.roman.kubik.exchangerates.domain.api

import com.roman.kubik.core.model.Result
import com.roman.kubik.exchangerates.domain.model.ExchangeRates

interface ExchangeRatesApiService {

    suspend fun getLatestExchangeRates(currencyCode: String): Result<ExchangeRates>

}