package com.roman.kubik.exchangeme.ui.base

import androidx.lifecycle.ViewModel
import com.roman.kubik.exchangeme.navigation.NavigationService

abstract class BaseViewModel: ViewModel() {

    lateinit var navigationService: NavigationService
}