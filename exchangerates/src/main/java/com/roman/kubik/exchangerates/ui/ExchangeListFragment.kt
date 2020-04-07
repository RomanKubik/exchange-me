package com.roman.kubik.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roman.kubik.exchangeme.activityComponent
import com.roman.kubik.exchangeme.dagger.viewModel
import com.roman.kubik.exchangerates.R
import com.roman.kubik.exchangerates.dagger.DaggerExchangeListComponent

/**
 * Fragment to display list of exchange rates
 */
class ExchangeListFragment : Fragment() {

    private val viewModel by viewModel {
        DaggerExchangeListComponent.factory().create(activityComponent).exchangeListViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exchange_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.test()
    }

}