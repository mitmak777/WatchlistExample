package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.domain.model.ForexDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlin.random.Random

class LocalFxRealTimeUpdateDatasource {

    private var isRunning = true
    fun getForexRealTimeUpdate(pairs: List<ForexDetail>): Flow<Result<List<ForexDetail>>> {
        return flow{
            while(currentCoroutineContext().isActive ) {
                delay(1000)
                // simulate the response from the server
                if(isRunning) {
                    emit(Result.success(pairs.map {
                        val nextPrice = it.prevPrice.toDouble() * Random.nextDouble(0.9, 1.1)
                        ForexDetail(
                            it.forexPair,
                            nextPrice.toBigDecimal(),
                            (nextPrice * Random.nextDouble(0.9, 1.0)).toBigDecimal(),
                            (nextPrice * Random.nextDouble(1.0, 1.1)).toBigDecimal(),
                            it.prevPrice,
                            it.timestamp
                        )
                    }))
                }
            }
        }.flowOn(Dispatchers.IO)

    }

    fun stopRealTimeUpdate(){
        isRunning = false
    }

    fun resumeRealTimeUpdate(){
        isRunning = true
    }
}