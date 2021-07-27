package com.rudolfhladik.kmm.template.data.store

import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.data.Endpoint
import com.rudolfhladik.kmm.template.data.api.RestApiManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.model.response.CoinResponse
import com.rudolfhladik.kmm.template.data.model.response.CoinsResponse
import kotlinx.coroutines.flow.Flow

class CoinStore internal constructor(
    private val api: RestApiManager,
    private val db: DatabaseManager
) {
    suspend fun fetchCoins(): CoinsResponse = api.executeRequest(Endpoint.GetCoins())

    suspend fun storeCoins(coins: List<CoinResponse>) = db.insertAll(coins.map {
        Coin(
            it.id, it.name, it.icon, it.symbol, it.price
        )
    })

    fun observeCoins(): Flow<List<Coin>> = db.getAll()
}
