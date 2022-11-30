package com.example.watchlistexample.data.interfaces

import com.example.watchlistexample.domain.model.AccountFxItem
import com.example.watchlistexample.domain.model.ForexDetail

interface AccountBalanceRepo {
    suspend fun getAccountFxList(availableFx : List<ForexDetail>) : List<AccountFxItem>
}