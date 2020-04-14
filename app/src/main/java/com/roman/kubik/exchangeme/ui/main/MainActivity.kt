package com.roman.kubik.exchangeme.ui.main

import android.os.Bundle
import com.roman.kubik.exchangeme.R
import com.roman.kubik.exchangeme.activityComponent
import com.roman.kubik.exchangeme.dagger.viewModel
import com.roman.kubik.exchangeme.ui.base.BaseActivity
import com.roman.kubik.exchangeme.ui.base.BaseViewModel

/**
 * Main activity. Application entry point
 */
class MainActivity : BaseActivity() {

    private val viewModel by viewModel { activityComponent.mainActivityViewModel }
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.toCurrenciesRates()
    }
}