package com.rudolfhladik.kmm.template.domain

import app.futured.arkitekt.kmusecases.usecase.FlowUseCase
import com.rudolfhladik.kmm.template.data.api.GraphQLManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.store.TripStore
import kotlinx.coroutines.flow.Flow

class ObserveBookedTripsUseCase : FlowUseCase<Unit, String>() {
    private val tripStore: TripStore = TripStore(GraphQLManager, DatabaseManager)

    override fun build(arg: Unit): Flow<String> = tripStore.observeBookedTrips()
}