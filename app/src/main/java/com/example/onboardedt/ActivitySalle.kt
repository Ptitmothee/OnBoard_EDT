package com.example.onboardedt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SalleActivity : AppCompatActivity() {
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
