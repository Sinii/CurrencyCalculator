package com.example.usecase.exchangerates

import com.example.base.usecase.BaseUseCase
import com.example.repository.exchangerates.ExchangeRatesRepository
import javax.inject.Inject

class GetExchangeRatesUseCase
@Inject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository
) : BaseUseCase<GetExchangeRatesUseCase.Params, GetExchangeRatesUseCase.Result>() {
    override suspend fun doWork(params: Params): Result {
        val result = exchangeRatesRepository
            .doWork(ExchangeRatesRepository.Params(params.baseCurrency))
        val rates = result.rates
        val errorMessage = result.error?.error
        return Result(rates, errorMessage)
    }

    class Params(val baseCurrency: String)
    class Result(val exchangeRates: Map<String, Double>?, val errorMessage: String?)
}