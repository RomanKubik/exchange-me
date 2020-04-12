package com.roman.kubik.exchangerates.domain.usecase

import com.roman.kubik.core.model.Result
import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.model.ExchangeRates
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

class ExchangeRatesUseCaseImpl @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : ExchangeRatesUseCase {

    companion object {
        const val REFRESH_DELAY = 1000L
    }

    private var latestRates = ArrayList<CurrencyRate>()

    override fun changeResponder(currency: CurrencyRate, amount: BigDecimal) {
        latestRates.remove(latestRates.find { it.currency == currency.currency })
        latestRates.add(
            0,
            CurrencyRate(
                currency.baseCurrency,
                currency.currency,
                currency.exchangeRate.divide(amount)
            )
        )
    }

    override fun getRates(currency: Currency, amount: BigDecimal): Flow<Result<List<CurrencyRate>>> =
        flow {
            while (true) {
                val result = exchangeRatesRepository.getLatestExchangeRates(currency)
                if (result is Result.Success) {
                    handleSuccess(result)
                    emit(Result.Success(applyMultiplierAmount(amount)))
                } else {
                    emit(Result.Error((result as Result.Error).exception))
                }
                delay(REFRESH_DELAY)
            }
        }

    private fun handleSuccess(result: Result.Success<ExchangeRates>) {
        val list = ArrayList<CurrencyRate>()
        val baseCurrency = result.data.baseCurrency
        if (latestRates.isEmpty()) {
            list.add(
                0,
                CurrencyRate(baseCurrency, baseCurrency, BigDecimal.ONE)
            )
            result.data.rates.forEach { c ->
                list.add(CurrencyRate(baseCurrency, c.key, c.value))
            }
        } else {
            latestRates.forEach { rate ->
                val r = result.data.rates[rate.currency] ?: BigDecimal.ONE
                list.add(CurrencyRate(baseCurrency, rate.currency, r))
            }
        }
        latestRates = list
    }

    private fun applyMultiplierAmount(amount: BigDecimal): List<CurrencyRate> {
        return latestRates.map {
            CurrencyRate(
                it.baseCurrency,
                it.currency,
                it.exchangeRate.multiply(amount)
            )
        }
    }

}