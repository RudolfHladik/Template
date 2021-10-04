package com.rudolfhladik.kmm.template.domain

import app.futured.arkitekt.kmusecases.freeze
import app.futured.arkitekt.kmusecases.usecase.UseCase
import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.data.api.RestApiManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.model.ListWrapper
import com.rudolfhladik.kmm.template.data.store.CoinStore

class GetCoinsListUseCase : UseCase<Unit, ListWrapper<Coin>>() {
    private val coinStore: CoinStore = CoinStore(RestApiManager, DatabaseManager)

    init {
        freeze()
    }

    override suspend fun build(arg: Unit): ListWrapper<Coin> {
        val list = coinStore.fetchCoins().coins.map {
            Coin(
                it.id,
                it.name,
                it.icon,
                it.symbol,
                it.price
            )
        }
        return ListWrapper(list)
    }
}
