package com.roman.kubik.exchangeme.navigation

import android.util.Log
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
        Log.d("MyTag", "To exchange list: " + activity.javaClass.simpleName)

        val fragment = Class.forName("com.roman.kubik.exchangerates.ui.ExchangeListFragment")
            .getConstructor()
            .newInstance() as Fragment

        activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.rootContent, fragment)
            .commit()
    }
}