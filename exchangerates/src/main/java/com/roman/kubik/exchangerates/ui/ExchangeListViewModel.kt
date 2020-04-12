package com.roman.kubik.exchangerates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.kubik.core.data.CoroutinesDispatcherProvider
import com.roman.kubik.core.model.Result
import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.model.ExchangeRates
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import com.roman.kubik.exchangerates.domain.usecase.ExchangeRatesUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of {@link ExchangeListFragment}
 */
class ExchangeListViewModel @Inject constructor(
    private val exchangeRatesUseCase: ExchangeRatesUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private var job: Job? = null
    private var baseCurrency: Currency = Currency.EUR
    private var baseAmount: Double = 100.0

    private val ratesLiveData: MutableLiveData<List<CurrencyRate>> = MutableLiveData()
    val rates: LiveData<List<CurrencyRate>> = ratesLiveData

    init {
        restartFetchExchangeRates()
    }

    fun changeResponder(currencyRate: CurrencyRate) {
        baseCurrency = currencyRate.currency
        baseAmount = currencyRate.exchangeRate
        exchangeRatesUseCase.changeResponder(currencyRate)
        restartFetchExchangeRates()
    }

    fun editAmount(currencyRate: CurrencyRate, amount: Double) {
        this.baseAmount = amount
        this.baseCurrency = currencyRate.currency
        restartFetchExchangeRates()
    }

    private fun restartFetchExchangeRates() {
        job?.cancel()
        job = viewModelScope.launch(coroutinesDispatcherProvider.io) {
            exchangeRatesUseCase.getRates(baseCurrency, baseAmount).collect { result ->
                if (result is Result.Success) {
                    ratesLiveData.postValue(result.data)
                } else {
                    handleError(result as Result.Error)
                }
            }
        }
    }

    private fun handleSuccess(result: Result.Success<ExchangeRates>) {
//        val list = ArrayList<CurrencyRate>()
//        if (rates.value.isNullOrEmpty()) {
//            list.add(
//                0,
//                CurrencyRate(baseCurrency, result.data.baseCurrency, baseAmount)
//            )
//            result.data.rates.forEach { c ->
//                list.add(CurrencyRate(baseCurrency, c.key, c.value * baseAmount))
//            }
//            ratesLiveData.postValue(list)
//        } else {
//            rates.value?.forEach { rate ->
//                val r = result.data.rates[rate.currency]?.times(baseAmount)
//                    ?: rate.exchangeRate
//                list.add(CurrencyRate(baseCurrency, rate.currency, r))
//            }
//        }
//        ratesLiveData.postValue(list)
    }

    private fun handleError(error: Result.Error) {

    }
}