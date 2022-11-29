package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.domain.ForexDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalFxRealTimeUpdateDatasource {
    fun getForexRealTimeUpdate(pairs: List<ForexDetail>): Flow<Result<List<ForexDetail>>> {
        return flow{
            delay(1000)
            emit(Result.success(pairs))
        }

    }
}