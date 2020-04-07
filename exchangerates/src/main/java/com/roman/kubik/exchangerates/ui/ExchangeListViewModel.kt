package com.roman.kubik.exchangerates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roman.kubik.core.data.CoroutinesDispatcherProvider
import com.roman.kubik.core.model.Result
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.GlobalScope
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

    private val ratesLiveData: MutableLiveData<List<CurrencyRate>> = MutableLiveData()
    val rates: LiveData<List<CurrencyRate>> = ratesLiveData

    init {
        GlobalScope.launch(coroutinesDispatcherProvider.io) {
            while (true) {
                val result = exchangeRatesRepository.getLatestExchangeRates("EUR")
                if (result is Result.Success) {
                    ratesLiveData.postValue(result.data.rates)
                }
                delay(1000)
            }
        }
    }
}