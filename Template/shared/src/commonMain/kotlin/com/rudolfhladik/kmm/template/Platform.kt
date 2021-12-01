package com.rudolfhladik.kmm.template

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*

expect class Platform() {
    val platform: String
}

expect fun createDriver() : SqlDriver
expect fun createHttpClient() : HttpClient
