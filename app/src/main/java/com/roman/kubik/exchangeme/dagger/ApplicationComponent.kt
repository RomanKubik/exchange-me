package com.roman.kubik.exchangeme.dagger

import android.content.Context
import com.roman.kubik.exchangeme.ui.main.MainActivityViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Main application component
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    val string: String


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): ApplicationComponent
    }

}