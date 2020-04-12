package com.roman.kubik.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.roman.kubik.exchangeme.activityComponent
import com.roman.kubik.exchangeme.dagger.viewModel
import com.roman.kubik.exchangerates.R
import com.roman.kubik.exchangerates.dagger.DaggerExchangeListComponent
import com.roman.kubik.exchangerates.domain.model.CurrencyRate
import kotlinx.android.synthetic.main.fragment_exchange_list.*
import java.math.BigDecimal

/**
 * Fragment to display list of exchange rates
 */
class ExchangeListFragment : Fragment(), ExchangeItemCallback {

    companion object {
        const val TOP_ITEM_POSITION = 0
    }

    private val viewModel by viewModel {
        DaggerExchangeListComponent.factory().create(activityComponent).exchangeListViewModel
    }
    private val adapter = ExchangeRatesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exchange_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        (listExchangeList.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        listExchangeList.layoutManager = LinearLayoutManager(context)
        listExchangeList.adapter = adapter
        viewModel.rates.observe(
            viewLifecycleOwner,
            Observer(adapter::submitList)
        )
    }

    override fun onResponderChanged(currencyRate: CurrencyRate) {
        viewModel.changeResponder(currencyRate)
        listExchangeList.scrollToPosition(TOP_ITEM_POSITION)
    }

    override fun onAmountEdited(currencyRate: CurrencyRate, amount: BigDecimal) {
        viewModel.editAmount(currencyRate, amount)
    }

}