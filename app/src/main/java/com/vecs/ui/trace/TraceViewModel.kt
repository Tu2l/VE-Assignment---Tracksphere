package com.vecs.ui.trace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vecs.data.models.Vehicle
import com.vecs.data.repositories.VehicleRepository

class TraceViewModel : ViewModel() {
    private val repository = VehicleRepository()

    private val _selectedItem = MutableLiveData<Vehicle>()
    val selectedItem: LiveData<Vehicle> = _selectedItem

    fun updateSelectedItem(vehicle: Vehicle) {
        _selectedItem.value = vehicle
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
}