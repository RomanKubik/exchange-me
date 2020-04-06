package com.roman.kubik.exchangeme.dagger

import com.roman.kubik.exchangeme.navigation.NavigationService
import com.roman.kubik.exchangeme.navigation.NavigationServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Provides activity specific dependencies
 */
@Module
object ActivityModule {

    @Provides
    @JvmStatic
    fun provideNavigationService(navigationServiceImpl: NavigationServiceImpl): NavigationService = navigationServiceImpl
}