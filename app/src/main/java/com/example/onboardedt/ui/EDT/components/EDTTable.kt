package com.example.onboardedt.ui.EDT.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.onboardedt.ui.EDT.EDTViewModel

@Composable
fun EDTTable(viewModel: EDTViewModel) {
    val horaires = listOf("M1", "M2", "M3", "M4", "S1", "S2", "S3", "S4", "S5", "S6")
    val selectedDay by viewModel.selectedDay.collectAsState()
    val currentWeek by viewModel.currentWeek.collectAsState()
    val reservations by viewModel.reservations.collectAsState()

    val filteredReservations = reservations.filter { it.jour == selectedDay && it.semaine == currentWeek }
    val salles = filteredReservations.map { it.salle }.distinct()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Header avec noms des salles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Créneau",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            salles.forEach { salle ->
                Text(
                    text = salle,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // Affichage des créneaux horaires et réservations
        horaires.forEach { creneau ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                // Colonne des créneaux horaires
                Text(
                    text = creneau,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                salles.forEach { salle ->
                    val res = filteredReservations.find { it.salle == salle && it.creneaux.contains(creneau) }

                    if (res != null) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height((res.creneaux.size * 40).dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${res.matiere} (${res.type})",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f).height(40.dp))
                    }
                }
            }
        }
    }
}
