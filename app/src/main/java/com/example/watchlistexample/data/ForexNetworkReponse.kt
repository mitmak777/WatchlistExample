package com.example.watchlistexample.data

import com.google.gson.annotations.SerializedName

data class ForexNetworkReponse(

    @SerializedName("rates") var rates: Map<String, RateDetail>? = null,
    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String = ""
)

data class RateDetail(
    var pair: String = "",
    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("timestamp") var timestamp: Int? = null


)