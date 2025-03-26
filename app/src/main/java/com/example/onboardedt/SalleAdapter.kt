package com.example.onboardedt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SalleAdapter(private var salles: List<Salle>) :
    RecyclerView.Adapter<SalleAdapter.SalleViewHolder>() {

    private val selectedSalles = mutableSetOf<String>() // Stocke les salles cochées

    inner class SalleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_select)
        val nomSalle: TextView = itemView.findViewById(R.id.nomSalle)
        val typeSalle: TextView = itemView.findViewById(R.id.typeSalle)
        val placesSalle: TextView = itemView.findViewById(R.id.placesSalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_salle, parent, false)
        return SalleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalleViewHolder, position: Int) {
        val salle = salles[position]
        holder.nomSalle.text = salle.nom
        holder.typeSalle.text = salle.type
        holder.placesSalle.text = salle.places.toString()

        // Gère l'état de la CheckBox
        holder.checkBox.setOnCheckedChangeListener(null) // Évite les bugs de recyclage
        holder.checkBox.isChecked = selectedSalles.contains(salle.nom)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedSalles.add(salle.nom)
            } else {
                selectedSalles.remove(salle.nom)
            }
        }
    }

    override fun getItemCount(): Int = salles.size

    fun getSelectedSalles(): List<String> {
        return selectedSalles.toList()
    }

    // Met à jour les données affichées
    fun updateData(newList: List<Salle>) {
        salles = newList
        notifyDataSetChanged()
    }
}
