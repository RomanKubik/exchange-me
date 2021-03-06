package com.roman.kubik.exchangerates.ui

import androidx.lifecycle.*
import com.roman.kubik.core.data.CoroutinesDispatcherProvider
import com.roman.kubik.currency.Currency
import com.roman.kubik.exchangeme.ui.base.BaseViewModel
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import com.roman.kubik.exchangerates.domain.model.CurrencyRatesResult
import com.roman.kubik.exchangerates.domain.usecase.ExchangeRatesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

/**
 * ViewModel of {@link ExchangeListFragment}
 */
class ExchangeListViewModel @Inject constructor(
    private val exchangeRatesUseCase: ExchangeRatesUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel(), LifecycleObserver {

    private var job: Job? = null
    private var baseCurrency: Currency = Currency.EUR
    private var baseAmount: BigDecimal = BigDecimal.valueOf(100.0)

    private val resultLiveData = MutableLiveData<CurrencyRatesResult>()
    val result: LiveData<CurrencyRatesResult> = resultLiveData

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startFetching() {
        fetchExchangeRates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopFetching() {
        job?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun changeResponder(currencyRate: CurrencyRate) {
        job?.cancel()
        exchangeRatesUseCase.changeResponder(currencyRate, baseAmount)
        baseCurrency = currencyRate.currency
        baseAmount = currencyRate.exchangeRate
        fetchExchangeRates()
    }

    fun editAmount(currencyRate: CurrencyRate, amount: BigDecimal) {
        job?.cancel()
        this.baseAmount = amount
        this.baseCurrency = currencyRate.currency
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        job = viewModelScope.launch(coroutinesDispatcherProvider.io) {
            exchangeRatesUseCase.getRates(baseCurrency, baseAmount).collect { result ->
                if (isActive) {
                    resultLiveData.postValue(result)
                }
            }
        }
    }
}