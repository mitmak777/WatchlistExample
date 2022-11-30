package com.example.watchlistexample.domain.model

import java.math.BigDecimal

data class AccountFxItem(
    val currencyPair: String,
    val amount: BigDecimal,
    val balance: BigDecimal
)
