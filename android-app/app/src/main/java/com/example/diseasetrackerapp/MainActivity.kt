package com.example.diseasetrackerapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.EditText
import android.view.View
import com.example.diseasetrackerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiseaseCaseAdapter
    private lateinit var etFilter: EditText
    private val viewModel: DiseaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        adapter = DiseaseCaseAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        etFilter = binding.etFilter

        viewModel.cases.observe(this) { cases ->
            if (cases.isEmpty()) {
                recyclerView.visibility = View.GONE
                binding.tvErrorMessage.visibility = View.VISIBLE
                binding.tvErrorMessage.text = "No data available. Please check your internet connection."
            } else {
                recyclerView.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
                adapter.updateData(cases)
            }
        }

        etFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                val filteredCases = viewModel.cases.value?.filter { case ->
                    query.isEmpty() || case.diseaseClassification?.contains(query, ignoreCase = true) == true
                } ?: emptyList()

                if (filteredCases.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = "No matching records found."
                } else {
                    recyclerView.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                    adapter.updateData(filteredCases)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.fetchCases()

        val btnCreateRecord = binding.btnCreateRecord
        btnCreateRecord.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_record, null)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Create New Record")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    val spGender = dialogView.findViewById<Spinner>(R.id.spGender)
                    val etAge = dialogView.findViewById<EditText>(R.id.etAge)
                    val spDiseaseClassification = dialogView.findViewById<Spinner>(R.id.spDiseaseClassification)

                    val clientGender = spGender.selectedItem?.toString() ?: ""
                    val clientAge = etAge.text.toString().toIntOrNull()
                    val diseaseClassification = spDiseaseClassification.selectedItem?.toString() ?: ""

                    if (clientGender.isNotEmpty() && clientAge in 1..120 && diseaseClassification.isNotEmpty()) {
                        val currentDate = getCurrentDate()

                        val newCase = DiseaseCase(
                            dateRecorded = currentDate,
                            clientGender = clientGender,
                            clientAge = clientAge,
                            diseaseClassification = diseaseClassification
                        )

                        viewModel.viewModelScope.launch {
                            try {
                                RetrofitInstance.api.createCase("67f3e466636df6b1f15d955a", newCase)
                                Log.d("CreateRecord", "Record created successfully")

                                val currentCases = viewModel.cases.value.orEmpty().toMutableList()
                                currentCases.add(newCase)
                                adapter.notifyItemInserted(currentCases.size - 1)

                                recyclerView.visibility = View.VISIBLE
                                binding.tvErrorMessage.visibility = View.GONE
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(this@MainActivity, "Failed to create record: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Please ensure all fields are valid", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
    }
}