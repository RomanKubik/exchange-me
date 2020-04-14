package com.roman.kubik.exchangeme.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roman.kubik.exchangeme.activityComponent

abstract class BaseActivity: AppCompatActivity() {

    abstract fun getViewModel(): BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().navigationService = activityComponent.navigationService
    }
}