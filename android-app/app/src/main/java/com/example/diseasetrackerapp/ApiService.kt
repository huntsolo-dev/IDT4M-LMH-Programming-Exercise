package com.example.diseasetrackerapp

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("rest/cases-disease")
    suspend fun getCases(@Header("x-apikey") apiKey: String): List<DiseaseCase>

    @POST("rest/cases-disease")
    suspend fun createCase(
        @Header("x-apikey") apiKey: String,
        @Body case: DiseaseCase
    ): Response<DiseaseCase>
}