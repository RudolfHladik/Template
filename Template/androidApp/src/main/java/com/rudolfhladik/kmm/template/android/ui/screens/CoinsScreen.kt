package com.rudolfhladik.kmm.template.android.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoinsScreen(viewModel: CoinsViewModel = CoinsViewModel()) {
    Column(Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(16.dp),
            onClick = { viewModel.fetchCoins() }
        ) {
            Text(text = "Load data")
        }
        Text(text = "Booked trips: ${viewModel.bookedTrips}")
        LazyColumn {
            items(viewModel.launches) { launch ->
                Text(
                    text = launch.id,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.bookTrip(launch) }
                        .padding(8.dp)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(items = viewModel.coins) { item ->
                    Text(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(4.dp),
                        text = item.name
                    )
                }
            })
    }
}
