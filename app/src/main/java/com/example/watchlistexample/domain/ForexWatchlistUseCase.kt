package com.example.watchlistexample.domain

import kotlinx.coroutines.flow.Flow


interface ForexWatchlistUseCase {
    suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>>
    suspend fun getEquity()

}