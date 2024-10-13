package com.vecs.ui.trace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vecs.data.models.Vehicle
import com.vecs.data.repositories.VehicleRepository

class TraceViewModel : ViewModel() {
    private val repository = VehicleRepository()


    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private val _selectedItem = MutableLiveData<String>()
    val selectedItem: LiveData<String> = _selectedItem

    fun updateSelectedItem(vehicleId: String) {
        _selectedItem.value = vehicleId
    }

    fun loadRunningVehiclesCount(): LiveData<Int> {
        return repository.getRunningVehiclesCount()
    }

    fun loadStoppedVehiclesCount(): LiveData<Int> {
        return repository.getStoppedVehiclesCount()
    }

    fun loadIdleVehiclesCount(): LiveData<Int> {
        return repository.getIdleVehiclesCount()
    }

    fun loadOfflineVehiclesCount(): LiveData<Int> {
        return repository.getOfflineVehiclesCount()
    }

    fun loadVehicles(): LiveData<List<Vehicle>> {
        return repository.getVehicles()
    }

    fun loadVehicleById(vehicleId: String): LiveData<Vehicle> {
        return repository.getVehicleById(vehicleId)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}