package com.example.diseasetrackerapp

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DiseaseCase(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("Date Recorded") val dateRecorded: String = getCurrentDate(),
    @SerializedName("Client Gender") val clientGender: String? = null,
    @SerializedName("Client Age") val clientAge: Int? = null,
    @SerializedName("Disease Classification") val diseaseClassification: String? = null
) {
    companion object {
        fun getCurrentDate(): String {
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        }
    }
}