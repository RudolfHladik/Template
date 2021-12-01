package com.rudolfhladik.kmm.template

import app.futured.arkitekt.kmusecases.scope.CoroutineScopeOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel : ArkitektViewModel()

open class ArkitektViewModel : CoroutineScopeOwner {
    private val job = SupervisorJob()

    override val coroutineScope: CoroutineScope = CoroutineScope(job + Dispatchers.Main)

    fun onDestroy() {
        job.cancel()
    }
}
