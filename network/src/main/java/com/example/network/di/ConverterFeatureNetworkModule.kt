package com.example.network.di

import com.example.network.retrofit.RetrofitExchangeRates
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ConverterFeatureNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitExchangeRates(retrofit: Retrofit): RetrofitExchangeRates = retrofit
        .create(RetrofitExchangeRates::class.java)
}
