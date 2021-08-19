package com.rudolfhladik.kmm.template

import platform.UIKit.UIDevice
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.rudolfhladik.kmm.template.db.CommonDatabase
import com.squareup.sqldelight.db.SqlDriver

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun createDriver(): SqlDriver = NativeSqliteDriver(CommonDatabase.Schema, "commondb.db")
