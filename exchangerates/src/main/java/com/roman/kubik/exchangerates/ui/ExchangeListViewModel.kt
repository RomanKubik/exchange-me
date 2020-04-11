package com.roman.kubik.exchangerates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.kubik.core.data.CoroutinesDispatcherProvider
import com.roman.kubik.core.model.Result
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of {@link ExchangeListFragment}
 */
class ExchangeListViewModel @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private var job: Job? = null
    private var baseCurrency: String = "EUR"
    private var baseAmount: Double = 100.0

    private val ratesLiveData: MutableLiveData<ArrayList<CurrencyRate>> = MutableLiveData()
    val rates: LiveData<ArrayList<CurrencyRate>> = ratesLiveData

    init {
        startGettingExchangeRates()
    }

    fun editCurrency(currencyRate: CurrencyRate) {
        ratesLiveData.value?.remove(currencyRate)
        ratesLiveData.value?.add(0, currencyRate)
        baseCurrency = currencyRate.currency
        baseAmount = currencyRate.exchangeRate
        startGettingExchangeRates()
    }

    fun editAmount(currencyRate: CurrencyRate, amount: Double) {
        this.baseAmount = amount
        this.baseCurrency = currencyRate.currency
        startGettingExchangeRates()
    }

    private fun startGettingExchangeRates() {
        job?.cancel()
        job = GlobalScope.launch(coroutinesDispatcherProvider.io) {
            while (true) {
                val result = exchangeRatesRepository.getLatestExchangeRates(baseCurrency)
                if (result is Result.Success) {
                    val list = ArrayList<CurrencyRate>()
                    if (rates.value.isNullOrEmpty()) {
                        list.add(
                            0,
                            CurrencyRate(baseCurrency, result.data.baseCurrency, baseAmount)
                        )
                        result.data.rates.forEach { c ->
                            list.add(CurrencyRate(baseCurrency, c.key, c.value * baseAmount))
                        }
                    } else {
                        for (rate in rates.value!!) {
                            val r = result.data.rates[rate.currency]?.times(baseAmount)
                                ?: rate.exchangeRate
                            list.add(CurrencyRate(baseCurrency, rate.currency, r))

                        }
                    }
                    ratesLiveData.postValue(list)
                }
                delay(1000)
            }
        }
    }
}