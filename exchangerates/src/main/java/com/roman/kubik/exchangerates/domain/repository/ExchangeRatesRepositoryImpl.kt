package com.roman.kubik.exchangerates.domain.repository

import com.roman.kubik.core.model.Result
import com.roman.kubik.exchangerates.domain.api.ExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.model.ExchangeRates
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(private val exchangeRatesApiService: ExchangeRatesApiService): ExchangeRatesRepository {

    override suspend fun getLatestExchangeRates(currencyCode: String): Result<ExchangeRates> {
        return exchangeRatesApiService.getLatestExchangeRates(currencyCode)
    }
}