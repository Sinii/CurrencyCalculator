package com.example.usecase.exchangerates

import com.example.rates.ExchangeRateItem
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCalculatedRateItemsUseCaseTest {

    @Test
    fun `no errors`() {
        val doConvertExchangeRateToRateItemUseCase = DoConvertExchangeRateToRateItemUseCase()
        val getExchangeRateToRateItemUseCase = mockkClass(GetExchangeRatesUseCase::class)
        val exchangeRateItem = ExchangeRateItem("EUR", "title", 1.0, null, null)
        val exchangeRate = emptyMap<String, Double>()
        every {
            runBlocking {
                getExchangeRateToRateItemUseCase.doWork(any())
            }
        } returns GetExchangeRatesUseCase.Result(exchangeRate, null)
        val useCase =
            GetCalculatedRateItemsUseCase(getExchangeRateToRateItemUseCase, doConvertExchangeRateToRateItemUseCase)
        runBlocking {
            val result = useCase.doWork(GetCalculatedRateItemsUseCase.Params(exchangeRateItem))
            assert(result.errorMessage == null)
        }
    }

    @Test
    fun `no items`() {
        val doConvertExchangeRateToRateItemUseCase = DoConvertExchangeRateToRateItemUseCase()
        val getExchangeRateToRateItemUseCase = mockkClass(GetExchangeRatesUseCase::class)
        val exchangeRateItem = ExchangeRateItem("EUR", "title", 1.0, null, null)
        val exchangeRate = emptyMap<String, Double>()
        every {
            runBlocking {
                getExchangeRateToRateItemUseCase.doWork(any())
            }
        } returns GetExchangeRatesUseCase.Result(exchangeRate, null)
        val useCase =
            GetCalculatedRateItemsUseCase(getExchangeRateToRateItemUseCase, doConvertExchangeRateToRateItemUseCase)
        runBlocking {
            val result = useCase.doWork(GetCalculatedRateItemsUseCase.Params(exchangeRateItem))
            assert(result.exchangeRatesItems == listOf(exchangeRateItem))
        }
    }


    @Test
    fun `one item`() {
        val doConvertExchangeRateToRateItemUseCase = DoConvertExchangeRateToRateItemUseCase()
        val getExchangeRateToRateItemUseCase = mockkClass(GetExchangeRatesUseCase::class)
        val exchangeRateItem = ExchangeRateItem("EUR", "EUR", 1.0, null, null)
        val exchangeRate = mapOf(Pair(exchangeRateItem.code, exchangeRateItem.amount))
        every {
            runBlocking {
                getExchangeRateToRateItemUseCase.doWork(any())
            }
        } returns GetExchangeRatesUseCase.Result(exchangeRate, null)
        val useCase =
            GetCalculatedRateItemsUseCase(getExchangeRateToRateItemUseCase, doConvertExchangeRateToRateItemUseCase)
        runBlocking {
            val result = useCase.doWork(GetCalculatedRateItemsUseCase.Params(exchangeRateItem))
            assert(result.exchangeRatesItems == listOf(exchangeRateItem, exchangeRateItem))
        }
    }
}