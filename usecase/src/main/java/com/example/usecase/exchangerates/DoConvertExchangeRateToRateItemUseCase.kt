package com.example.usecase.exchangerates

import com.example.base.usecase.BaseUseCase
import com.example.rates.ExchangeRateItem
import javax.inject.Inject

class DoConvertExchangeRateToRateItemUseCase
@Inject constructor() :
    BaseUseCase<DoConvertExchangeRateToRateItemUseCase.Params, DoConvertExchangeRateToRateItemUseCase.Result>() {
    override suspend fun doWork(params: Params): Result {
        val rate = params.amount * params.rateViaBaseCurrency
        return Result(ExchangeRateItem(params.currencyCode, params.currencyCode, rate, null))
    }

    class Params(val amount: Double, val rateViaBaseCurrency: Double, val currencyCode: String)
    class Result(val exchangeRateItem: ExchangeRateItem)
}