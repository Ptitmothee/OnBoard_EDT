package com.example.onboardedt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.onboardedt.ui.EDT.EDTScreen
import com.example.onboardedt.ui.EDT.EDTViewModel

class EDTActivity : ComponentActivity() {
    private val viewModel: EDTViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EDTScreen(viewModel)
        }
    }
}
