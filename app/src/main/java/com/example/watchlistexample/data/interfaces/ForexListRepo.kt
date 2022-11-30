package com.example.watchlistexample.data.interfaces

import com.example.watchlistexample.domain.model.ForexDetail
import kotlinx.coroutines.flow.Flow

interface ForexListRepo {
    suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>>
    fun resumeRealTimeUpdate()
    fun stopRealTimeUpdate()
}