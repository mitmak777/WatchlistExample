package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.domain.ForexDetail
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.isActive
import kotlinx.coroutines.isActive
import java.math.BigDecimal
import kotlin.random.Random

class LocalFxRealTimeUpdateDatasource {
    fun getForexRealTimeUpdate(pairs: List<ForexDetail>): Flow<Result<List<ForexDetail>>> {
        return flow{
            while(currentCoroutineContext().isActive) {
                delay(1000)
                emit(Result.success(pairs.map {
                    val nextPrice = it.currentPrice.toDouble() * Random.nextDouble(0.9, 1.1)
                    ForexDetail(
                        it.forexPair,
                        nextPrice.toBigDecimal(),
                        (nextPrice * Random.nextDouble(1.0, 1.1)).toBigDecimal(),
                        (nextPrice * Random.nextDouble(0.9, 1.0)).toBigDecimal(),
                        it.prevPrice,
                        it.timestamp
                    )
                }))
            }
        }

    }
}