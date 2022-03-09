package com.rudolfhladik.kmm.template.domain

import app.futured.arkitekt.kmusecases.freeze
import app.futured.arkitekt.kmusecases.usecase.UseCase
import com.rudolfhladik.kmm.template.data.api.GraphQLManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.model.ListWrapper
import com.rudolfhladik.kmm.template.data.model.response.Launch
import com.rudolfhladik.kmm.template.data.store.TripStore

class GetLaunchesListUseCase : UseCase<Unit, ListWrapper<Launch>>() {
    private val tripStore: TripStore = TripStore(GraphQLManager, DatabaseManager)

    init {
        freeze()
    }

    override suspend fun build(arg: Unit): ListWrapper<Launch> =
        ListWrapper(tripStore.getLaunches())
}