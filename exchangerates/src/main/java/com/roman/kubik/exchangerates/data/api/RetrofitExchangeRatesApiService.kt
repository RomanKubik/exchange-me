package com.roman.kubik.exchangerates.data.api

import com.roman.kubik.core.model.Result
import com.roman.kubik.exchangerates.domain.api.ExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.model.ExchangeRates
import java.io.IOException
import javax.inject.Inject

class RetrofitExchangeRatesApiService @Inject constructor(private val apiService: ExchangeRatesApi) :
    ExchangeRatesApiService {

    override suspend fun getLatestExchangeRates(currencyCode: String): Result<ExchangeRates> {
        val response = apiService.getLatest(currencyCode)
        return if (response.isSuccessful && response.body() != null) {
            val rates =
                ExchangeRates(
                    response.body()?.baseCurrency ?: currencyCode,
                    response.body()?.rates?.map { CurrencyRate(it.key, it.value) } ?: emptyList()
                )
            Result.Success(rates)
        } else {
            Result.Error(IOException())
        }
    }
}