package com.vecs.data.repositories

import com.vecs.data.models.Fuel
import com.vecs.data.models.FuelType
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus
import kotlin.random.Random

class DemoDataSource {
    val demoVehicles = (1..10).map {
        Vehicle(
            primaryVehicleId = (10000..99999).random().toString(),
            secondaryVehicleId = "Vehicle-$it",
            vehicleStatus = VehicleStatus.values().random(),
            adblueFuel = Fuel(type = FuelType.Adblue, quantity = (0..100).random(), capacity = 100),
            dieselFuel = Fuel(type = FuelType.Diesel, quantity = (0..200).random(), capacity = 200),
            chargerVoltage = 220f + (240f - 220f) * Random.nextFloat(),
            speed = 100f * Random.nextFloat(),
            waitTime = 60f * Random.nextFloat(),
            mileage = 1000f + (100000f - 1000f) * Random.nextFloat(),
            location = "City-$it",
            lastUpdated = "2023-12-19T${(0..23).random()}:${(0..59).random()}:00Z"
        )
    }
}