package com.roman.kubik.exchangerates.dagger

import com.roman.kubik.exchangeme.dagger.ActivityComponent
import com.roman.kubik.exchangeme.dagger.FeatureScope
import com.roman.kubik.exchangerates.ui.ExchangeListViewModel
import dagger.Component

/**
 * Component of Exchange List feature
 */
@Component(dependencies = [ActivityComponent::class], modules = [ExchangeRatesModule::class])
@FeatureScope
interface ExchangeListComponent {

    val exchangeListViewModel: ExchangeListViewModel

    @Component.Factory
    interface Factory {
        fun create(
            activityComponent: ActivityComponent
        ): ExchangeListComponent
    }
}