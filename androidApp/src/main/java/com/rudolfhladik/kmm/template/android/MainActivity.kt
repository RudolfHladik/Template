package com.rudolfhladik.kmm.template.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.rudolfhladik.kmm.template.android.ui.screens.CoinsScreen
import com.rudolfhladik.kmm.template.appContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext

        setContent {
            CoinsScreen()
        }
    }
}
