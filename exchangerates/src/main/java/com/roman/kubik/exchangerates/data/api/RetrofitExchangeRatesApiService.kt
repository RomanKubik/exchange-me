package com.roman.kubik.exchangerates.data.api

import com.roman.kubik.core.model.Result
import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangerates.domain.api.ExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.model.ExchangeRates
import java.io.IOException
import java.lang.IllegalArgumentException
import javax.inject.Inject

class RetrofitExchangeRatesApiService @Inject constructor(private val apiService: ExchangeRatesApi) :
    ExchangeRatesApiService {

    override suspend fun getLatestExchangeRates(currency: Currency): Result<ExchangeRates> {
        return try {
            val response = apiService.getLatest(currency.code)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val rates =
                    ExchangeRates(
                        currency,
                        mapStringToCurrency(body.rates)
                    )
                Result.Success(rates)
            } else {
                Result.Error(IOException("Couldn't get exchange rates: ${response.code()}, ${response.errorBody()}"))
            }
        } catch (exception: Exception) {
            Result.Error(IOException("Couldn't get exchange rates", exception))
        }
    }

    private fun mapStringToCurrency(rates: Map<String, Double>): Map<Currency, Double> {
        val map = HashMap<Currency, Double>()
        rates.forEach {
            try {
                map[Currency.valueOf(it.key)] = it.value
            } catch (exception: IllegalArgumentException) {
                /* ignore for unknown currencies */
            }
        }
        return map
    }
}