package com.vecs.data.models

data class Vehicle(
    val primaryVehicleId: String,
    val secondaryVehicleId: String,
    val vehicleStatus: VehicleStatus,
    val adblueFuel: Fuel,
    val dieselFuel: Fuel,
    val chargerVoltage: Float,
    val speed: Float,
    val waitTime: Float,
    val mileage: Float,
    val location: String,
    val lastUpdated: String
)
