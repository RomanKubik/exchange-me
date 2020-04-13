package com.roman.kubik.exchangerates.data.api.model

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("baseCurrency")
    val baseCurrency: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)