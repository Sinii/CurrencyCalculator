package com.example.usecase.exchangerates

import com.example.rates.ExchangeRateItem
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DoConvertExchangeRateToRateItemUseCaseTest {

    @Test
    fun `check one exchangeRateItem`() {
        val useCase = DoConvertExchangeRateToRateItemUseCase()
        val exchangeRateItem = ExchangeRateItem("EUR", "title", 1.0, null, null)
        val rate = 2.0
        runBlocking {
            val result = useCase
                .doWork(
                    DoConvertExchangeRateToRateItemUseCase.Params(
                        exchangeRateItem.amount,
                        rate,
                        exchangeRateItem.code
                    )
                )
            assert(result.exchangeRateItem.amount == rate * exchangeRateItem.amount)
        }
    }
}