package com.vecs.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus

class VehicleRepository {
    private val dataSource = DemoDataSource

    /**
     * This method is can be extended to retrieve data from remote source as well as local source,
     * currently it loading data from demoSource
     **/
    fun getVehicles(): LiveData<List<Vehicle>> {
        return MutableLiveData(dataSource.vehicleData)
    }

    /*
     * Returns the vehicle with the given id from the datasource
     */
    fun getVehicleById(id: String): LiveData<Vehicle> {
        return MutableLiveData(dataSource.vehicleData.find { it.primaryVehicleId == id })
    }

    /*
     * Returns the number of Running vehicles from the datasource
     */
    fun getRunningVehiclesCount(): LiveData<Int> {
        return MutableLiveData(getVehiclesCountByStatus(VehicleStatus.Running))
    }

    /*
    * Returns the number of Stopped vehicles from the datasource
    */
    fun getStoppedVehiclesCount(): LiveData<Int> {
        return MutableLiveData(getVehiclesCountByStatus(VehicleStatus.Stopped))
    }

    /*
     * Returns the number of Idle vehicles from the datasource
     */
    fun getIdleVehiclesCount(): LiveData<Int> {
        return MutableLiveData(getVehiclesCountByStatus(VehicleStatus.Idle))
    }

    /*
     * Returns the number of Offline vehicles from the datasource
     */
    fun getOfflineVehiclesCount(): LiveData<Int> {
        return MutableLiveData(getVehiclesCountByStatus(VehicleStatus.Offline))
    }

    /*
     * Returns the number of vehicles of a particular status from the datasource
     */
    private fun getVehiclesCountByStatus(status: VehicleStatus): Int {
        return dataSource.vehicleData.count { it.vehicleStatus == status }
    }
}