package com.example.watchlistexample.domain

import com.example.watchlistexample.data.ForexAPIService
import com.example.watchlistexample.data.ForexNetworkReponse
import java.lang.reflect.Constructor
import javax.inject.Inject

class ForexWatchlistUseCase @Inject constructor(private val forexAPIService: ForexAPIService) {

    suspend fun getForex(pairs: List<String>): ForexNetworkReponse {
        return forexAPIService.getForex(pairs.joinToString(","))
    }
}