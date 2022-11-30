package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.data.interfaces.AccountBalanceRepo
import com.example.watchlistexample.data.model.AccountFxItem
import com.example.watchlistexample.domain.ForexDetail
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class AccountBalanceRepoImpl @Inject constructor(): AccountBalanceRepo {
    override suspend fun getAccountFxList(availableFx: List<ForexDetail>): List<AccountFxItem> {
        return availableFx.map {
            AccountFxItem(it.forexPair, BigDecimal("10000").divide(it.currentPrice, 10, RoundingMode.CEILING),BigDecimal("10000"))
        }
    }
}