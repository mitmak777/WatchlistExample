package com.example.watchlistexample.data.model

import java.math.BigDecimal

data class AccountFxItem(
    val currencyPair: String,
    val amount: BigDecimal
)
