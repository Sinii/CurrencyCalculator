package com.example.network.retrofit

import com.example.rates.ExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitExchangeRates {

    @GET("/latest")
    suspend fun getRatesAsync(
        @Query("base") baseCurrency: String
    ): Response<ExchangeRatesResponse>

}
