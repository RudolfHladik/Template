package com.rudolfhladik.kmm.template.android.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.futured.arkitekt.kmusecases.scope.CoroutineScopeOwner
import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.domain.ObserveCoinsListUseCase
import kotlinx.coroutines.CoroutineScope

class CoinsViewModel : ViewModel(), CoroutineScopeOwner {
    private val getCoinsUseCase = ObserveCoinsListUseCase()
    var coins by mutableStateOf(emptyList<Coin>())

    override val coroutineScope: CoroutineScope
        get() = viewModelScope

    fun fetchCoins() {
        getCoinsUseCase.execute(Unit) {
            onNext { coins = it.list }
        }
    }
}
