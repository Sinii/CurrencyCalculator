package com.example.rates

class ExchangeRatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)