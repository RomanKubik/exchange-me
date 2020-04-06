package com.roman.kubik.exchangeme.dagger

import dagger.Module
import dagger.Provides

/**
 * Provides application specific dependencies
 */
@Module
class ApplicationModule {

    @Provides
    fun getString(): String = "Testy"
}