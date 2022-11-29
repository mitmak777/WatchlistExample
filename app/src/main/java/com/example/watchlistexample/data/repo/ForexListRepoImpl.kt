package com.example.watchlistexample.data.repo

import com.example.watchlistexample.data.interfaces.ForexListRepo
import com.example.watchlistexample.data.datasource.ForexAPIService
import com.example.watchlistexample.data.datasource.LocalFxRealTimeUpdateDatasource
import com.example.watchlistexample.domain.ForexDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import java.time.LocalDate.now
import java.util.*
import javax.inject.Inject

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
                                value.rate ?: BigDecimal.ZERO,
                                value.rate ?: BigDecimal.ZERO,
                                value.rate ?: BigDecimal.ZERO,
                                value.rate ?: BigDecimal.ZERO,
                                value.timestamp ?: Date().time
                            )
                        }
                        emit(Result.success(forexList))
                    }
                }

            } catch (e: Exception) {
                emit(Result.failure(e))
            }

        }
    }

}