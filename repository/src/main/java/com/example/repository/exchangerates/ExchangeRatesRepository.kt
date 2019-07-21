package com.example.repository.exchangerates

import com.example.ApiError
import com.example.base.repository.BaseRepository
import com.example.network.retrofit.RetrofitExchangeRates
import javax.inject.Inject

class ExchangeRatesRepository
@Inject constructor(
    private val retrofitExchangeRates: RetrofitExchangeRates
) : BaseRepository<ExchangeRatesRepository.Params, ExchangeRatesRepository.Result>() {
    override suspend fun doWork(params: Params): Result {
        val response = retrofitExchangeRates.getRatesAsync(params.baseCurrency)
        val result = response.body()
        val error = response.errorBody()

        return Result(result?.rates, getError(error))
    }

    class Params(val baseCurrency: String)
    class Result(val rates: Map<String, Double>?, val error: ApiError?)
}