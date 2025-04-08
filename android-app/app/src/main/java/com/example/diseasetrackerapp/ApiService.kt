package com.example.diseasetrackerapp

import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Header

interface ApiService {
    @GET("rest/cases-disease")
    suspend fun getCases(@Header("x-apikey") apiKey: String): Response<List<DiseaseCase>>

    @POST("rest/cases-disease")
    suspend fun createCase(
        @Header("x-apikey") apiKey: String,
        @Body case: DiseaseCase
    ): Response<DiseaseCase>
}