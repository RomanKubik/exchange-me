package com.roman.kubik.exchangeme.dagger

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Main application component
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    val retrofit: Retrofit

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): ApplicationComponent
    }

}