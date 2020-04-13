package com.roman.kubik.exchangeme.ui.main

import com.roman.kubik.exchangeme.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * ViewModel of {@link MainActivity}
 */
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    fun toCurrenciesRates() {
        navigationService.toExchangeList()
    }
}