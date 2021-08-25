package com.rudolfhladik.kmm.template

import com.squareup.sqldelight.db.SqlDriver

expect class Platform() {
    val platform: String
}

expect fun createDriver() : SqlDriver
