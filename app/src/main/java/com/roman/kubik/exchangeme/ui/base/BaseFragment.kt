package com.roman.kubik.exchangeme.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.roman.kubik.exchangeme.activityComponent

abstract class BaseFragment: Fragment() {

    abstract fun getViewModel(): BaseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getViewModel().navigationService = activityComponent.navigationService
    }
}