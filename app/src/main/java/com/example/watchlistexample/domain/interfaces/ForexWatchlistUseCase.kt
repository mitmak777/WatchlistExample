package com.example.watchlistexample.domain.interfaces

import com.example.watchlistexample.domain.model.AccountFxItem
import com.example.watchlistexample.domain.model.ForexDetail
import kotlinx.coroutines.flow.Flow


interface ForexWatchlistUseCase {
    suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>>
    suspend fun  getEquity(fxList: List<ForexDetail>): Result<List<AccountFxItem>>
    fun stopRealTimeUpdate()
    fun resumeRealTimeUpdate()
}