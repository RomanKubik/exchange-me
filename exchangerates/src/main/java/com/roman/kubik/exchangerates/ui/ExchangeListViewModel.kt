package com.roman.kubik.exchangerates.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.roman.kubik.exchangeme.navigation.NavigationService
import javax.inject.Inject

/**
 * ViewModel of {@link ExchangeListFragment}
 */
class ExchangeListViewModel @Inject constructor(
    private val str: String,
    private val navigationService: NavigationService
) : ViewModel() {

    fun test() {
        Log.d("MyTagFragment", str)
    }
}