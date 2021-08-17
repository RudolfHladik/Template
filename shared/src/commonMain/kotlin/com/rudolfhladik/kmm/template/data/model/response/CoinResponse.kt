package com.rudolfhladik.kmm.template.data.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class CoinsResponse(
    val coins: List<CoinResponse>,
)

@Serializable
internal data class CoinResponse(
    val id: String,
    val name: String,
    val icon: String,
    val symbol: String,
    val price: Double
)
