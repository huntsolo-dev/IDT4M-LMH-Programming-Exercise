package com.example.diseasetrackerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiseaseCaseAdapter(private var cases: List<DiseaseCase>) :
    RecyclerView.Adapter<DiseaseCaseAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val tvDateRecorded: TextView = itemView.findViewById(R.id.tvDateRecorded)
        val tvClientGender: TextView = itemView.findViewById(R.id.tvClientGender)
        val tvClientAge: TextView = itemView.findViewById(R.id.tvClientAge)
        val tvDiseaseClassification: TextView = itemView.findViewById(R.id.tvDiseaseClassification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_disease_case, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val case = cases[position]
        holder.tvId.text = "ID: ${case.id ?: "N/A"}"
        holder.tvDateRecorded.text = "Date Recorded: ${case.dateRecorded ?: "No Date Recorded"}"
        holder.tvClientGender.text = "Client Gender: ${case.clientGender ?: "No Gender"}"
        holder.tvClientAge.text = "Client Age: ${case.clientAge?.toString() ?: "Unknown Age"}"
        holder.tvDiseaseClassification.text = "Disease Classification: ${case.diseaseClassification ?: "No Classification"}"
    }

    override fun getItemCount(): Int = cases.size

    fun updateData(newCases: List<DiseaseCase>) {
        cases = newCases
        notifyDataSetChanged()
    }
}