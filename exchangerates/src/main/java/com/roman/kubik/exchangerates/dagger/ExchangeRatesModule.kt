package com.roman.kubik.exchangerates.dagger

import com.roman.kubik.exchangerates.data.api.ExchangeRatesApi
import com.roman.kubik.exchangerates.data.api.RetrofitExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.api.ExchangeRatesApiService
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepository
import com.roman.kubik.exchangerates.domain.repository.ExchangeRatesRepositoryImpl
import com.roman.kubik.exchangerates.domain.usecase.ExchangeRatesUseCase
import com.roman.kubik.exchangerates.domain.usecase.ExchangeRatesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object ExchangeRatesModule {

    @Provides
    @JvmStatic
    @Reusable
    fun provideExchangeRatesRepository(repository: ExchangeRatesRepositoryImpl): ExchangeRatesRepository =
        repository

    @Provides
    @JvmStatic
    @Reusable
    fun provideExchangeRatesApiService(apiService: RetrofitExchangeRatesApiService): ExchangeRatesApiService =
        apiService

    @Provides
    @JvmStatic
    @Reusable
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesApi {
        return retrofit.create(ExchangeRatesApi::class.java)
    }

    @Provides
    @JvmStatic
    fun provideExchangeRatesUseCase(useCase: ExchangeRatesUseCaseImpl): ExchangeRatesUseCase =
        useCase
}