package com.example.di.module

import com.example.constants.MapiUrls
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(MapiUrls.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .client(client)
        .build()
}