package com.rudolfhladik.kmm.template.domain

import app.futured.arkitekt.kmusecases.freeze
import app.futured.arkitekt.kmusecases.usecase.FlowUseCase
import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.data.api.RestApiManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.model.ListWrapper
import com.rudolfhladik.kmm.template.data.store.CoinStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveCoinsListUseCase : FlowUseCase<Unit, ListWrapper<Coin>>() {
    private val coinStore: CoinStore = CoinStore(RestApiManager(), DatabaseManager())

    init {
        freeze()
    }

    override fun build(arg: Unit): Flow<ListWrapper<Coin>> = merge(
        fetchCoinsAndStoreLocally(),
        coinStore.observeCoins()
    )
        .map { ListWrapper(it) }

    private fun fetchCoinsAndStoreLocally(): Flow<List<Coin>> = flow {
        val coinsResponse = coinStore.fetchCoins()
        coinStore.storeCoins(coinsResponse.coins)
    }
}
