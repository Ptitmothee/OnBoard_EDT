package com.example.onboardedt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            OnBoardEDTTheme {
//                SalleSelectionView(
//                )
//            }
//        }
//    }
//}

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SalleAdapter
    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button
    private lateinit var validateButton: Button

    private val salles = listOf(
        Salle("Amphi A", "AMPHI", 200),
        Salle("Salle 101", "COURS", 30),
        Salle("Salle Info 2", "INFO", 20),
        Salle("Amphi B", "AMPHI", 150),
        Salle("Salle 102", "COURS", 25)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_salles)

        initUI()
        setupRecyclerView()
        setupSearch()
        setupValidation() // Ajout de la validation
    }

    // Initialise les composants graphiques
    private fun initUI() {
        recyclerView = findViewById(R.id.recyclerView)
        searchInput = findViewById(R.id.searchInput)
        searchButton = findViewById(R.id.searchButton)
        validateButton = findViewById(R.id.validateButton) // Bouton de validation
    }

    // Configure le RecyclerView
    private fun setupRecyclerView() {
        adapter = SalleAdapter(salles)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // Gère la recherche
    private fun setupSearch() {
        searchButton.setOnClickListener {
            val query = searchInput.text.toString().lowercase()
            val filteredList = salles.filter {
                it.nom.lowercase().contains(query) || it.type.lowercase().contains(query)
            }
            adapter.updateData(filteredList)
        }
    }

    private fun setupValidation() {
        validateButton.setOnClickListener {
            val selectedSalles = adapter.getSelectedSalles()
            Toast.makeText(this, "Salles sélectionnées : ${selectedSalles.joinToString()}", Toast.LENGTH_LONG).show()
        }
    }
}

// Modèle de salle
data class Salle(val nom: String, val type: String, val places: Int, var isSelected: Boolean = false)

private val sampleSalles = listOf(
    Salle("Amphi A", "AMPHI", 200),
    Salle("Salle 101", "COURS", 30),
    Salle("Salle 102", "COURS", 30),
    Salle("Salle 201", "INFO", 20),
    Salle("Salle 202", "INFO", 20),
    Salle("Salle 301", "LABO", 15),
    Salle("Salle 302", "LABO", 15),
    Salle("Salle 401", "RÉUNION", 10),
    Salle("Salle 402", "RÉUNION", 10),
    Salle("Salle 501", "EXAMEN", 50),
    Salle("Salle 502", "EXAMEN", 50)
)

@Preview
@Composable
fun SalleSelectionPreview() {
    SalleSelectionView()
}

@Composable
fun SalleSelectionView() {

    lateinit var tableLayout: TableLayout
    lateinit var spinner: Spinner
    lateinit var prevPageButton: Button
    lateinit var nextPageButton: Button
    lateinit var pageIndicator: TextView

    var searchQuery by remember { mutableStateOf("") }
    var salles by remember { mutableStateOf(sampleSalles) }
    var selectedSalles by remember { mutableStateOf(setOf<String>()) }
    var sortColumn by remember { mutableStateOf("Nom") }
    var ascending by remember { mutableStateOf(true) }

    var currentPage = 0
    var rowsPerPage = 5

    Column(modifier = Modifier.padding(16.dp)) {
        HeaderView()
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(searchQuery) { searchQuery = it }
        Spacer(modifier = Modifier.height(16.dp))
        SalleTable(
            salles = salles,
            searchQuery = searchQuery,
            selectedSalles = selectedSalles,
            sortColumn = sortColumn,
            ascending = ascending,
            onSortChange = {
                if (sortColumn == it) {
                    ascending = !ascending
                } else {
                    sortColumn = it
                    ascending = true
                }
            },
            onSelectionChange = { selectedSalles = it }
        )
    }
}

@Composable
fun HeaderView() {
    var nom_vue = stringResource(R.string.nom_vue_liste_salles)
    Row (
    ) {
        Text("OnBoard")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = {}) { Text("Menu") }
        Text(nom_vue)
        Button(onClick = {}) { Text("Utilisateur") }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )
        Button(onClick = {}) { Text("Rechercher") }
    }
}



// Cellule de tableau
@Composable
fun TableCell(content: String) {
    Box(
        modifier = Modifier
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = content)
    }
}

// Cellule d'en-tête du tableau
@Composable
fun TableHeaderCell(
    title: String,
    sortColumn: String,
    ascending: Boolean,
    onSortChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onSortChange(title) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = title)
        if (sortColumn == title) {
            val arrow = if (ascending) "↑" else "↓"
            Text(text = " $arrow")
        }
    }
}

@Composable
fun SalleTable(
    salles: List<Salle>,
    searchQuery: String,
    selectedSalles: Set<String>,
    sortColumn: String,
    ascending: Boolean,
    onSortChange: (String) -> Unit,
    onSelectionChange: (Set<String>) -> Unit
) {
    // Filtre les salles par nom et type en fonction de la recherche
    val filteredSalles = salles.filter {
        it.nom.contains(searchQuery, ignoreCase = true) ||
        it.type.contains(searchQuery, ignoreCase = true)
    }
    // Trie les salles filtrées
    val sortedSalles = filteredSalles.sortedWith(compareBy<Salle> {
        when (sortColumn) {
            "Nom" -> it.nom
            "Type" -> it.type
            "Places" -> it.places.toString()
            else -> it.nom
        }
    }.let { if (ascending) it else it.reversed() })

    Column {
        // En-tête du tableau
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(vertical = 8.dp)
        ) {
            TableHeaderCell("Nom", sortColumn, ascending, onSortChange)
            TableHeaderCell("Type", sortColumn, ascending, onSortChange)
            TableHeaderCell("Places", sortColumn, ascending, onSortChange)
        }
        HorizontalDivider(thickness = 1.dp, color = Color.Black)

        sortedSalles.forEach { salle ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                TableCell(salle.nom)
                TableCell(salle.type)
                TableCell(salle.places.toString())
            }
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
        }
    }
}
