package com.example.watchlistexample.data.datasource

import com.example.watchlistexample.data.ForexNetworkReponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ForexAPIService {

    @GET("api/live")
    suspend fun getForex(@Query("pairs") pairs: String): ForexNetworkReponse
}