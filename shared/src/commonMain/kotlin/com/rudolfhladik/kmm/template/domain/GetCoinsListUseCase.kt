package com.rudolfhladik.kmm.template.domain

import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.data.api.RestApiManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.store.CoinStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinsListUseCase {
    private val coinStore: CoinStore = CoinStore(RestApiManager(), DatabaseManager())

    fun getCoinsList(): Flow<List<Coin>> = merge(
        fetchCoinsAndStoreLocally(),
        coinStore.observeCoins()
    )

    private fun fetchCoinsAndStoreLocally(): Flow<List<Coin>> = flow {
        val coinsResponse = coinStore.fetchCoins()
        coinStore.storeCoins(coinsResponse.coins)
    }
}
