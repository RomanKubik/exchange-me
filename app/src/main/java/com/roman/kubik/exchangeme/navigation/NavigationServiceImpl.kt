package com.roman.kubik.exchangeme.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.roman.kubik.exchangeme.R
import javax.inject.Inject

/**
 * Implementation of {@link NavigationService}
 */
class NavigationServiceImpl @Inject constructor(private val activity: FragmentActivity) :
    NavigationService {

    override fun toExchangeList() {
        val fragment = Class.forName(Screens.EXCHANGE_RATES.className)
            .getConstructor()
            .newInstance() as Fragment

        activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.rootContent, fragment)
            .commit()
    }
}