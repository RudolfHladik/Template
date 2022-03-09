package com.rudolfhladik.kmm.template.android.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.futured.arkitekt.kmusecases.scope.CoroutineScopeOwner
import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.data.model.response.Launch
import com.rudolfhladik.kmm.template.domain.GetLaunchesListUseCase
import com.rudolfhladik.kmm.template.domain.ObserveBookedTripsUseCase
import com.rudolfhladik.kmm.template.domain.ObserveCoinsListUseCase
import com.rudolfhladik.kmm.template.domain.SetNewTripUseCase
import kotlinx.coroutines.CoroutineScope

class CoinsViewModel : ViewModel(), CoroutineScopeOwner {

    private val getCoinsUseCase = ObserveCoinsListUseCase()
    var coins by mutableStateOf(emptyList<Coin>())

    private val bookTripUseCase = SetNewTripUseCase()
    private val getLaunchesUseCase = GetLaunchesListUseCase()
    private val observeBookedTrips = ObserveBookedTripsUseCase()
    var launches by mutableStateOf(emptyList<Launch>())
    var bookedTrips by mutableStateOf("0")

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    init {
        fetchLaunches()
        observeTripsBooked()
    }

    fun fetchCoins() {
        getCoinsUseCase.execute(Unit) {
            onNext { coins = it.list }
        }
    }

    fun bookTrip(launch: Launch) {
        bookTripUseCase.execute(launch) {
            onSuccess { }
        }
    }

    private fun observeTripsBooked() {
        observeBookedTrips.execute(Unit) {
            onNext { bookedTrips = it }
        }
    }

    private fun fetchLaunches() {
        getLaunchesUseCase.execute(Unit) {
            onSuccess { launches = it.list }
        }
    }
}
