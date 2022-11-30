package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.data.interfaces.AccountBalanceRepo
import com.example.watchlistexample.domain.model.AccountFxItem
import com.example.watchlistexample.domain.model.ForexDetail
import java.math.BigDecimal
import javax.inject.Inject

class AccountBalanceRepoImpl @Inject constructor(): AccountBalanceRepo {
    override suspend fun getAccountFxList(availableFx: List<ForexDetail>): List<AccountFxItem> {
        return availableFx.map {
            AccountFxItem(it.forexPair, BigDecimal("10000").times(it.currentPrice),BigDecimal("10000"))
        }
    }
}