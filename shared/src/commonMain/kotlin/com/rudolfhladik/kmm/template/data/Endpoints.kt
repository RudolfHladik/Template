package com.rudolfhladik.kmm.template.data

import com.rudolfhladik.kmm.template.data.model.response.CoinsResponse
import com.rudolfhladik.kmm.template.tool.Constants
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer

sealed class Endpoint<R>(
    internal val url: String,
    internal val method: HttpMethod,
    internal val responseSerializer: KSerializer<R>? = null
) {
    class GetCoins(currency: String = "EUR") : Endpoint<CoinsResponse>(
        Constants.baseUrl + "public/v1/coins?currency=$currency",
        HttpMethod.Get,
        CoinsResponse.serializer()
    )
}
