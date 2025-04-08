package com.example.diseasetrackerapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DiseaseViewModel : ViewModel() {
    private val _cases = MutableLiveData<List<DiseaseCase>>()
    val cases: LiveData<List<DiseaseCase>> = _cases

    fun fetchCases() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCases("67f3e466636df6b1f15d955a")
                Log.d("APIResponse", "Fetched data: $response")
                _cases.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}