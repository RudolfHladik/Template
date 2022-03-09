package com.rudolfhladik.kmm.template.domain

import app.futured.arkitekt.kmusecases.freeze
import app.futured.arkitekt.kmusecases.usecase.UseCase
import com.rudolfhladik.kmm.template.data.api.GraphQLManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.model.response.Launch
import com.rudolfhladik.kmm.template.data.store.TripStore

class SetNewTripUseCase : UseCase<Launch, String>() {
    private val tripsStore: TripStore = TripStore(GraphQLManager, DatabaseManager)

    init {
        freeze()
    }

    override suspend fun build(arg: Launch): String =
        if (arg.isBooked) {
            tripsStore.cancelTrip(arg.id)
        } else {
            tripsStore.bookTrip(id = arg.id)
        }
}