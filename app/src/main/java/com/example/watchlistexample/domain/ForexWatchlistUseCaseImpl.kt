package com.example.watchlistexample.domain

import com.example.watchlistexample.data.datasource.ForexAPIService
import com.example.watchlistexample.data.interfaces.ForexListRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForexWatchlistUseCaseImpl @Inject constructor(
private val forexListRepo: ForexListRepo) : ForexWatchlistUseCase {

    override suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>> {
        return forexListRepo.getForexList(pairs)
    }

    override suspend fun getEquity() {
        TODO("Not yet implemented")
    }
}