package com.roman.kubik.exchangerates.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.roman.kubik.exchangeme.activityComponent
import com.roman.kubik.exchangeme.dagger.viewModel
import com.roman.kubik.exchangerates.dagger.DaggerExchangeListComponent

class ExchangeListFragment : Fragment() {

    private val viewModel by viewModel {
        DaggerExchangeListComponent.factory().create(activityComponent).exchangeListViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.test()
    }

}