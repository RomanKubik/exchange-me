package com.roman.kubik.exchangeme.dagger

import androidx.fragment.app.FragmentActivity
import com.roman.kubik.exchangeme.navigation.NavigationService
import com.roman.kubik.exchangeme.ui.main.MainActivityViewModel
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit

/**
 * Activity component
 */
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
@ActivityScope
interface ActivityComponent {

    val retrofit: Retrofit
    val navigationService: NavigationService
    val mainActivityViewModel: MainActivityViewModel

    @Component.Factory
    interface Factory {
        fun create(
            applicationComponent: ApplicationComponent,
            @BindsInstance activity: FragmentActivity
        ): ActivityComponent
    }
}