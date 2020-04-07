package com.roman.kubik.exchangerates.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.roman.kubik.core.data.CoroutinesDispatcherProvider
import com.roman.kubik.exchangeme.navigation.NavigationService
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel of {@link ExchangeListFragment}
 */
class ExchangeListViewModel @Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val navigationService: NavigationService,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    fun test() {
        GlobalScope.launch(coroutinesDispatcherProvider.io) {
            val result = exchangeRatesRepository.getLatestExchangeRates("EUR")
            withContext(coroutinesDispatcherProvider.main) {
                Log.d("MyTag", result.toString())
            }
        }
    }
}