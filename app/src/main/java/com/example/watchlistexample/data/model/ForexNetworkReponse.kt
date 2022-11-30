package com.example.watchlistexample.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ForexNetworkReponse(

    @SerializedName("rates") var rates: Map<String, RateDetail>? = null,
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message: String? = null
)

data class RateDetail(
    var pair: String = "",
    @SerializedName("rate") var rate: BigDecimal,
    @SerializedName("timestamp") var timestamp: Long

)