# IDT4M Last Mile Health Interview Programming Exercise by Pacharo Simukonda

## **Overview**
This repository contains the complete solution for the **IDT4M Last Mile Health Interview Programming Exercise**. The task involves building two components:  
1. An **Android app** that interacts with a REST API to fetch, filter, and create disease case records.  
2. A **web dashboard** that visualizes the same data disaggregated by gender and disease classification using charts and tables.

The project demonstrates proficiency in modern development practices, including REST API integration, responsive UI design, and data visualization.

---

## **Features**

### **Android App**
- **READ**: Fetch and display disease case data from the REST API in a scrollable `RecyclerView`.  
- **FILTER**: Dynamically filter records based on `Disease Classification` using an input field or dropdown.  
- **CREATE**: Add new disease case records with auto-generated dates (`Date Recorded`) and post them to the database.  

### **Web Dashboard**
- **VISUALIZE**: Display data disaggregated by:
  - **Gender**: Using a bar chart.  
  - **Disease Classification**: Using a pie chart.  
- **Responsive Design**: Built with **Tailwind CSS** to ensure compatibility across devices.  

---

## **Technologies Used**

### **Frontend**
- **Android App**: Kotlin, XML (for layouts), Retrofit, RecyclerView, MVVM Architecture.  
- **Web Dashboard**: HTML, Tailwind CSS, JavaScript, Chart.js.  

### **Backend Integration**
- **REST API**: [RestDB](https://restdb.io/) for fetching and posting disease case data.  
- **Authentication**: API key-based authentication (`x-apikey`).  

### **Libraries**
- **Android App**:  
  - Retrofit: For making API calls.  
  - Gson: For parsing JSON responses into Kotlin objects.  
  - Lifecycle-aware components: ViewModel and LiveData for managing UI-related data.  
- **Web Dashboard**:  
  - Chart.js: For creating interactive bar and pie charts.  

### **Architecture**
- **Android App**: MVVM (Model-View-ViewModel) architecture for separation of concerns and testability.  
- **Web Dashboard**: Standard web architecture with modular JavaScript for handling API calls and rendering charts.
