package com.roman.kubik.exchangerates.domain.usecase

import com.roman.kubik.core.model.Result
import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.model.CurrencyRatesResult
import com.roman.kubik.exchangerates.domain.model.ExchangeRates
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import java.math.MathContext
import javax.inject.Inject

class ExchangeRatesUseCaseImpl @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : ExchangeRatesUseCase {

    companion object {
        const val REFRESH_DELAY = 1000L
    }

    private val lockObject = Any()
    private var latestRates = ArrayList<CurrencyRate>()

    override fun changeResponder(currency: CurrencyRate, amount: BigDecimal) {
        synchronized(lockObject) {
            val c = latestRates.find { it.currency == currency.currency } ?: return
            latestRates.remove(c)
            latestRates.add(0, c)
        }
    }

    override fun getRates(currency: Currency, amount: BigDecimal): Flow<CurrencyRatesResult> =
        flow {
            while (true) {
                val result = exchangeRatesRepository.getLatestExchangeRates(currency)
                if (result is Result.Success) {
                    handleSuccess(result)
                    emit(CurrencyRatesResult(applyMultiplierAmount(amount)))
                } else {
                    handleError(currency)
                    emit(
                        CurrencyRatesResult(
                            applyMultiplierAmount(amount),
                            (result as Result.Error).exception
                        )
                    )
                }
                delay(REFRESH_DELAY)
            }
        }

    private fun handleSuccess(result: Result.Success<ExchangeRates>) {
        synchronized(lockObject) {
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
    }

    private fun handleError(currency: Currency) {
        synchronized(lockObject) {
            val targetRate = latestRates.findLast { it.currency == currency }?.exchangeRate ?: return
            val multiplier =
                if (targetRate.compareTo(BigDecimal.ZERO) == 0) {
                    return
                } else {
                    BigDecimal.ONE.divide(targetRate, MathContext.DECIMAL32)
                }
            val list = ArrayList<CurrencyRate>()
            latestRates.forEach { rate ->
                list.add(
                    CurrencyRate(
                        currency,
                        rate.currency,
                        multiplier.multiply(rate.exchangeRate)
                    )
                )
            }
            latestRates = list
        }
    }

    private fun applyMultiplierAmount(amount: BigDecimal): List<CurrencyRate> {
        synchronized(lockObject) {
            return latestRates.map {
                CurrencyRate(
                    it.baseCurrency,
                    it.currency,
                    it.exchangeRate.multiply(amount)
                )
            }
        }
    }

}