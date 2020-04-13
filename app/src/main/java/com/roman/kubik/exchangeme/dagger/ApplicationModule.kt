package com.roman.kubik.exchangeme.dagger

import com.roman.kubik.exchangeme.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides application specific dependencies
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun getRestService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}