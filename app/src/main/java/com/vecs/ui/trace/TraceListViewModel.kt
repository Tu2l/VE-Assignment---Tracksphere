package com.vecs.ui.trace

import androidx.core.graphics.values
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vecs.data.models.Fuel
import com.vecs.data.models.FuelType
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus
import com.vecs.data.repositories.VehicleRepository
import kotlin.random.Random

class TraceListViewModel : ViewModel() {
    private val repository = VehicleRepository()

    fun loadVehicles() : LiveData<List<Vehicle>>{
        return repository.getVehicles()
    }
}