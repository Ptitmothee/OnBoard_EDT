package com.example.onboardedt.ui.EDT.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.onboardedt.ui.EDT.EDTViewModel

@Composable
fun RoomsSelected(viewModel: EDTViewModel) {
    val salles by viewModel.sallesRecherchees.collectAsState()

    Text(
        text = "Salles : ${salles.joinToString(", ")}",
        style = MaterialTheme.typography.titleMedium, // Replaces subtitle1
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
