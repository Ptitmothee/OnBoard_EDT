package com.example.onboardedt.ui.EDT.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.onboardedt.ui.EDT.EDTViewModel

@Composable
fun DaySelector(viewModel: EDTViewModel) {
    val selectedDay by viewModel.selectedDay.collectAsState()
    val days = listOf("Lun", "Mar", "Mer", "Jeu", "Ven") // Jours abrégés
    val currentWeek by viewModel.currentWeek.collectAsState()

    // Mapping inverse : permet de convertir "Lundi" → "Lun", "Mardi" → "Ma", etc.
    val dayMappingInverse = mapOf(
        "Lundi" to "Lun",
        "Mardi" to "Mar",
        "Mercredi" to "Mer",
        "Jeudi" to "Jeu",
        "Vendredi" to "Ven"
    )

    val selectedShortDay = dayMappingInverse[selectedDay] // Récupère l'abréviation du jour sélectionné

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        days.forEach { day ->
            val dayNumber = viewModel.getDateForSelectedDay(currentWeek, day)

            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.selectDay(day) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (day == selectedShortDay) MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp), // Supprime le padding interne du bouton
                    modifier = Modifier
                        .size(60.dp) // Taille du bouton
                        .clip(CircleShape)
                ) {
                    Text(
                        text = day,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Affichage du numéro du jour
                Text(
                    text = dayNumber.toString(),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
