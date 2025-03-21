package com.example.onboardedt.ui.EDT

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.onboardedt.ui.EDT.components.WeekSelector
import com.example.onboardedt.ui.EDT.components.DaySelector
import com.example.onboardedt.ui.EDT.components.RoomsSelected
import com.example.onboardedt.ui.EDT.components.EDTTable


@Composable
fun EDTScreen(
    viewModel: EDTViewModel
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Sélecteur de semaine
        WeekSelector(viewModel)

        // Sélecteur de jours
        DaySelector(viewModel)

        // Salles recherchées
        RoomsSelected(viewModel)

        // Tableau de l'emploi du temps
        EDTTable(viewModel)
    }
}
