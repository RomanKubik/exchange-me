package com.roman.kubik.exchangeme.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roman.kubik.exchangeme.R
import com.roman.kubik.exchangeme.component
import com.roman.kubik.exchangeme.dagger.DaggerActivityComponent
import com.roman.kubik.exchangeme.dagger.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel { DaggerActivityComponent.factory().create(component, this).mainActivityViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.test()
    }
}