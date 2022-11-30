package com.example.watchlistexample.data.repo

import com.example.watchlistexample.data.interfaces.ForexListRepo
import com.example.watchlistexample.data.datasource.ForexAPIService
import com.example.watchlistexample.data.datasource.LocalFxRealTimeUpdateDatasource
import com.example.watchlistexample.domain.ForexDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.random.Random

class ForexListRepoImpl @Inject constructor(
    private val forexAPIService: ForexAPIService,
    private val realtimeSource: LocalFxRealTimeUpdateDatasource
) : ForexListRepo {
    override suspend fun getForexList(pairs: List<String>): Flow<Result<List<ForexDetail>>> {
        return flow {
            try {
                forexAPIService.getForex(pairs.joinToString(","))?.let {
                    it.rates?.let {
                        val forexList = it.map { (key, value) ->
                            ForexDetail(key,
                                value.rate ,
                                BigDecimal.valueOf(value.rate.toDouble() * Random.nextDouble(1.0, 1.1)),
                                BigDecimal.valueOf(value.rate.toDouble() * Random.nextDouble(0.9, 1.0)),
                                value.rate ,
                                value.timestamp
                            )
                        }
                        emit(Result.success(forexList))
                        emitAll(realtimeSource.getForexRealTimeUpdate(forexList))
                    }
                }

            } catch (e: Exception) {
                emit(Result.failure(e))
            }

        }
    }

}