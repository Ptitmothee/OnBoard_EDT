package com.example.onboardedt.ui.EDT.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.onboardedt.ui.EDT.EDTViewModel

@Composable
fun WeekSelector(viewModel: EDTViewModel) {
    val currentWeek by viewModel.currentWeek.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { viewModel.changeWeek(-1) }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Semaine précédente")
        }
        Text(text = "Semaine $currentWeek", style = MaterialTheme.typography.titleLarge) // Changed h6 -> titleLarge
        IconButton(onClick = { viewModel.changeWeek(1) }) {
            Icon(Icons.Filled.ArrowForward, contentDescription = "Semaine suivante")
        }
    }
}
