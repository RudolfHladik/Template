package com.rudolfhladik.kmm.template.data.store

import com.rudolfhladik.kmm.template.*
import com.rudolfhladik.kmm.template.data.api.GraphQLManager
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import com.rudolfhladik.kmm.template.data.model.response.Launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TripStore internal constructor(
    private val graphQlManager: GraphQLManager,
    private val dbManager: DatabaseManager,
) {
    suspend fun bookTrip(id: String): String {
        println("TripStore bookTrip: $id")
        loginIfNeeded()
        val data = graphQlManager.executeMutation(BookTripMutation(id))
        return data.bookTrips.message ?: "Trip not booked"
    }

    suspend fun cancelTrip(id: String): String {
        loginIfNeeded()
        val data = graphQlManager.executeMutation(CancelTripMutation(id))
        return data.cancelTrip.message ?: "Trip not canceled"
    }

    suspend fun getLaunches(): List<Launch> {
        val data = graphQlManager.executeQuery(LaunchesListQuery())
        return data.launches.launches.mapNotNull { it?.let { Launch(it.id, it.isBooked) } }
    }

    fun observeBookedTrips(): Flow<String> =
        graphQlManager.executeSubscription(TripsBookedSubscription())
            .map { it.tripsBooked.toString() }

    private suspend fun loginIfNeeded() {
        val token = dbManager.getToken()
        if (token == null) {
            val loginMutation = LoginMutation("rudolf.hladik@futured.app")
            val newToken = graphQlManager.executeMutation(loginMutation).login?.token
            newToken?.let { dbManager.insertToken(it) }
        }
    }
}