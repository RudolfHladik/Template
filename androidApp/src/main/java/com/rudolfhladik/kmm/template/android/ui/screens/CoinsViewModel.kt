package com.rudolfhladik.kmm.template.android.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudolfhladik.kmm.template.Coin
import com.rudolfhladik.kmm.template.domain.GetCoinsListUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class CoinsViewModel : ViewModel() {
    private val getCoinsUseCase = GetCoinsListUseCase()
    var coins by mutableStateOf(emptyList<Coin>())

    fun fetchCoins() {
        viewModelScope.launch {
            getCoinsUseCase.getCoinsList().collect {
                coins = it
            }
        }
    }
}
