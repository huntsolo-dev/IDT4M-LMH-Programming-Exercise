package com.example.diseasetrackerapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class DiseaseViewModel : ViewModel() {
    private val _cases = MutableLiveData<List<DiseaseCase>>()
    val cases: LiveData<List<DiseaseCase>> = _cases

    fun fetchCases() {
        viewModelScope.launch {
            try {
                val response: Response<List<DiseaseCase>> = RetrofitInstance.api.getCases("67f3e466636df6b1f15d955a")

                if (response.isSuccessful) {
                    val fetchedCases = response.body()
                    if (fetchedCases != null) {
                        _cases.value = fetchedCases
                    } else {
                        Log.e("DiseaseViewModel", "Response body is null")
                    }
                } else {
                    Log.e("DiseaseViewModel", "API call failed: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DiseaseViewModel", "Error fetching data: ${e.localizedMessage}")
            }
        }
    }
}