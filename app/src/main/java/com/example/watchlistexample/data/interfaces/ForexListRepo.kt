package com.example.watchlistexample.data.interfaces

import com.example.watchlistexample.domain.ForexDetail
import kotlinx.coroutines.flow.Flow

interface ForexListRepo {
    suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>>
}