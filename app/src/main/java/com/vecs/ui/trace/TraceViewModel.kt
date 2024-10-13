package com.vecs.ui.trace

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vecs.data.repositories.VehicleRepository

class TraceViewModel : ViewModel() {
    private val repository = VehicleRepository()

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
}