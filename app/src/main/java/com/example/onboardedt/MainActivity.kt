package com.example.onboardedt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.onboardedt.ui.theme.OnBoardEDTTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnBoardEDTTheme {
                SalleSelectionView(
                )
            }
        }
    }
}

// Modèle de salle
data class Salle(val nom: String, val type: String, val places: Int, var isSelected: Boolean = false)

@Preview
@Composable
fun SalleSelectionPreview() {
    SalleSelectionView()
}

@Composable
fun SalleSelectionView() {
    var searchQuery by remember { mutableStateOf("") }
    var salles by remember { mutableStateOf(sampleSalles) }
    var selectedSalles by remember { mutableStateOf(setOf<String>()) }
    var sortColumn by remember { mutableStateOf("Nom") }
    var ascending by remember { mutableStateOf(true) }

    Column(modifier = Modifier.padding(16.dp)) {
        HeaderView()
        SearchBar(searchQuery) { searchQuery = it }
        SalleTable(salles, searchQuery, selectedSalles, sortColumn, ascending, onSortChange = {
            if (sortColumn == it) ascending = !ascending
            else {
                sortColumn = it
                ascending = true
            }
        }, onSelectionChange = { selectedSalles = it })
    }
}

@Composable
fun HeaderView() {
    Row {
        Text("OnBoard")
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = {}) { Text("Menu") }
        Text(stringResource(R.string.nom_vue_liste_salles))
        Button(onClick = {}) { Text("Utilisateur") }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        BasicTextField(value = query, onValueChange = onQueryChange)
        Button(onClick = {}) { Text("Rechercher") }
    }
}

@Composable
fun SalleTable(
    salles: List<Salle>, searchQuery: String, selectedSalles: Set<String>,
    sortColumn: String, ascending: Boolean,
    onSortChange: (String) -> Unit, onSelectionChange: (Set<String>) -> Unit
) {
    val sortedSalles = salles.sortedWith(compareBy<Salle> {
        when (sortColumn) {
            "Nom" -> it.nom
            "Type" -> it.type
            "Places" -> it.places.toString()
            else -> it.nom
        }
    }.let { if (ascending) it else it.reversed() })

    Column {
        Row {
            listOf("Nom", "Type", "Places").forEach { column ->
                Button(onClick = { onSortChange(column) }) { Text(column) }
            }
        }
        sortedSalles.filter { it.nom.contains(searchQuery, ignoreCase = true) }.forEach { salle ->
            Row {
                Checkbox(
                    checked = salle.nom in selectedSalles,
                    onCheckedChange = {
                        onSelectionChange(if (it) selectedSalles + salle.nom else selectedSalles - salle.nom)
                    }
                )
                Text(salle.nom)
                Text(salle.type)
                Text(salle.places.toString())
            }
        }
    }
}

val sampleSalles = listOf(
    Salle("Amphi A", "AMPHI", 200),
    Salle("Salle Info 1", "INFO", 30),
    Salle("Salle Cours 5", "COURS", 50)
)
