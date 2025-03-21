package com.example.onboardedt.ui.EDT

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale

class EDTViewModel(application: Application) : AndroidViewModel(application) {
    // Semaine sélectionnée
    private val _currentWeek = MutableStateFlow(getCurrentWeek())
    val currentWeek = _currentWeek.asStateFlow()

    // Jour sélectionné
    private val _selectedDay = MutableStateFlow("Mardi")
    val selectedDay = _selectedDay.asStateFlow()

    // Liste des salles
    private val _sallesRecherchees = MutableStateFlow(listOf<String>())
    val sallesRecherchees = _sallesRecherchees.asStateFlow()

    // Liste des réservations
    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations = _reservations.asStateFlow()

    init {
        loadData(application)
    }

    fun changeWeek(delta: Int) {
        _currentWeek.value = (_currentWeek.value + delta).coerceIn(1, 52)
    }

    fun selectDay(day: String) {
        val dayMapping = mapOf(
            "Lun" to "Lundi",
            "Mar" to "Mardi",
            "Mer" to "Mercredi",
            "Jeu" to "Jeudi",
            "Ven" to "Vendredi"
        )

        _selectedDay.value = dayMapping[day] ?: _selectedDay.value
    }

    fun getCurrentWeek(): Int {
        val today = LocalDate.now()
        return today.get(WeekFields.of(Locale.FRANCE).weekOfYear())
    }

    fun getDateForSelectedDay(currentWeek: Int, selectedDay: String): Int {
        val today = LocalDate.now()
        val firstDayOfWeek = LocalDate.of(today.year, 1, 1)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .plusWeeks(currentWeek - 1L)

        val days = listOf("Lun", "Mar", "Mer", "Jeu", "Ven")
        val dayIndex = days.indexOf(selectedDay)
        return if (dayIndex != -1) firstDayOfWeek.plusDays(dayIndex.toLong()).dayOfMonth else -1
    }

    private fun loadData(application: Application) {
        val inputStream = application.assets.open("data.json")
        val reader = InputStreamReader(inputStream)
        val emploiDuTemps: EmploiDuTemps = Gson().fromJson(reader, object : TypeToken<EmploiDuTemps>() {}.type)

        _sallesRecherchees.value = emploiDuTemps.salles_recherchees
        _reservations.value = emploiDuTemps.emplois_du_temps.flatMap { salle ->
            salle.reservations.map { it.copy(salle = salle.salle) }
        }
    }
}

// Modèle JSON

data class EmploiDuTemps(
    val salles_recherchees: List<String>,
    val emplois_du_temps: List<Salle>
)

data class Salle(
    val salle: String,
    val reservations: List<Reservation>
)

data class Reservation(
    val jour: String,
    val semaine: Int,
    val salle: String,
    val creneaux: List<String>,
    val enseignant: String,
    val matiere: String,
    val type: String,
    val nombre_eleves: Int
)

