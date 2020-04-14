package com.roman.kubik.core.ui.util

import android.text.Editable
import android.text.TextWatcher

abstract class BaseTextWatcher: TextWatcher {

    override fun afterTextChanged(editable: Editable) {
    }

    override fun beforeTextChanged(string: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(string: CharSequence, start: Int, before: Int, count: Int) {
    }
}