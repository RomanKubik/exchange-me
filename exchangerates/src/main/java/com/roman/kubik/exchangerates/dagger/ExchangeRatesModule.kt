package com.roman.kubik.exchangerates.dagger

import com.roman.kubik.exchangerates.data.api.ExchangeRatesApi
import com.roman.kubik.exchangerates.data.api.RetrofitExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.api.ExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object ExchangeRatesModule {

    @Provides
    @JvmStatic
    fun provideExchangeRatesRepository(repository: ExchangeRatesRepositoryImpl): ExchangeRatesRepository =
        repository

    @Provides
    @JvmStatic
    fun provideExchangeRatesApiService(apiService: RetrofitExchangeRatesApiService): ExchangeRatesApiService =
        apiService

    @Provides
    @JvmStatic
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi {
        return retrofit.create(ExchangeRatesApi::class.java)
    }
}