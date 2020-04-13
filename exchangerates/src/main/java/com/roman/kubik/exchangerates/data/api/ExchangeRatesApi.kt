package com.roman.kubik.exchangerates.data.api

import com.roman.kubik.exchangerates.data.api.model.ExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET("latest")
    suspend fun getLatest(@Query("base") currencyCode: String): Response<ExchangeRatesResponse>

}