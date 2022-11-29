package com.example.watchlistexample.data.interfaces

import com.example.watchlistexample.data.model.AccountFxItem
import com.example.watchlistexample.domain.ForexDetail

interface AccountBalanceRepo {
    suspend fun getAccountFxList(availableFx : List<ForexDetail>) : List<AccountFxItem>
}