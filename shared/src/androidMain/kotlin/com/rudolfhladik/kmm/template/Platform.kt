package com.rudolfhladik.kmm.template

import android.content.Context
import com.rudolfhladik.kmm.template.db.CommonDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

lateinit var appContext: Context

actual fun createDriver(): SqlDriver =
     AndroidSqliteDriver(CommonDatabase.Schema, appContext, "commondb.db")
