package com.rudolfhladik.kmm.template.data.database

import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.createDriver
import com.rudolfhladik.kmm.template.db.CommonDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal class DatabaseManager {
    private val db: CommonDatabase = CommonDatabase(createDriver())

    fun getAll(): Flow<List<Coin>> = db.coinEntityQueries
        .selectAll()
        .asFlow()
        .mapToList()

    suspend fun insertAll(list: List<Coin>) {
        list.forEach {
            db.coinEntityQueries.insertCoin(it.id, it.name, it.icon, it.symbol, it.price)
        }
    }

    suspend fun deleteAll() = db.coinEntityQueries.deleteAll()
}
