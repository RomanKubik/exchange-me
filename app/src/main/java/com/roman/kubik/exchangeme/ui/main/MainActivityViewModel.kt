package com.roman.kubik.exchangeme.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.roman.kubik.exchangeme.navigation.NavigationService
import javax.inject.Inject

/**
 * ViewModel of {@link MainActivity}
 */
class MainActivityViewModel @Inject constructor(
    private val string: String,
    private val navigationService: NavigationService
) : ViewModel() {

    fun test() {
        Log.d("MyTag", string)
        navigationService.toExchangeList()
    }
}