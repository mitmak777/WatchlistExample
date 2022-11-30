package com.example.watchlistexample.domain

import java.math.BigDecimal

data class ForexDetail(
    val forexPair: String,
    val currentPrice: BigDecimal,
    val buyPrice: BigDecimal,
    val sellPrice: BigDecimal,
    val prevPrice: BigDecimal,
    val timestamp: Long
)
