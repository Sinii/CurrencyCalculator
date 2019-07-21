package com.example.rates

data class ExchangeRateItem(
    val code: String,
    val title: String,
    val amount: Double,
    val currencyUrl: String?,
    val selection: Int? = null
)