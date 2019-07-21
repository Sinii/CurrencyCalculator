package com.example.usecase.exchangerates

import com.example.base.usecase.BaseUseCase
import com.example.rates.ExchangeRateItem
import com.example.utils.dLog
import javax.inject.Inject

class GetCalculatedRateItemsUseCase
@Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
    private val doConvertExchangeRateToRateItemUseCase: DoConvertExchangeRateToRateItemUseCase
) : BaseUseCase<GetCalculatedRateItemsUseCase.Params, GetCalculatedRateItemsUseCase.Result>() {
    override suspend fun doWork(params: Params): Result {
        val result = getExchangeRatesUseCase
            .doWork(GetExchangeRatesUseCase.Params(params.selectedCurrency.code))
        val exchangeRatesItemsResult = arrayListOf(params.selectedCurrency)
        val exchangeRatesItems = result.exchangeRates?.map {
            val currencyCode = it.key
            val currencyRate = it.value
            //todo add icon url?
            //todo sort items via selectedCurrency
            "doConvertExchangeRateToRateItemUseCase params.selectedCurrency.amount = ${params.selectedCurrency.amount}".dLog()
            doConvertExchangeRateToRateItemUseCase
                .doWork(
                    DoConvertExchangeRateToRateItemUseCase.Params(
                        params.selectedCurrency.amount,
                        currencyRate,
                        currencyCode
                    )
                )
                .exchangeRateItem
        }
        "GetCalculatedRateItemsUseCase exchangeRatesItems = $exchangeRatesItems exchangeRatesItemsResult = $exchangeRatesItemsResult".dLog()
        if (exchangeRatesItems != null) {
            exchangeRatesItemsResult.addAll(exchangeRatesItems)
        }
        return Result(exchangeRatesItemsResult, result.errorMessage)
    }

    class Params(val selectedCurrency: ExchangeRateItem)
    class Result(val exchangeRatesItems: List<ExchangeRateItem>?, val errorMessage: String?)
}