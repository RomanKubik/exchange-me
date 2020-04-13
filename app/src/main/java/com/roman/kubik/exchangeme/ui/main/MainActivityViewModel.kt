package com.roman.kubik.exchangeme.ui.main

import androidx.lifecycle.ViewModel
import com.roman.kubik.exchangeme.navigation.NavigationService
import javax.inject.Inject

/**
 * ViewModel of {@link MainActivity}
 */
class MainActivityViewModel @Inject constructor(
    private val navigationService: NavigationService
) : ViewModel() {

    fun test() {
        navigationService.toExchangeList()
    }
}