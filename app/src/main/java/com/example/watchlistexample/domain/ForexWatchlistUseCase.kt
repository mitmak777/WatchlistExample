package com.example.watchlistexample.domain

import com.example.watchlistexample.data.model.AccountFxItem
import kotlinx.coroutines.flow.Flow


interface ForexWatchlistUseCase {
    suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>>
    suspend fun  getEquity(fxList: List<ForexDetail>): Result<List<AccountFxItem>>

}