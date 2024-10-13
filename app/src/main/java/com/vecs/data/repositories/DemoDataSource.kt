package com.vecs.data.repositories

import com.vecs.data.models.Fuel
import com.vecs.data.models.FuelType
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus
import kotlin.random.Random

object DemoDataSource {
    val vehicleData by lazy { getDemoVehicles() }

    private fun getDemoVehicles() = (1..10).map {
        Vehicle(
            primaryVehicleId = "KA02HG${(10000..99999).random()}",
            secondaryVehicleId = "MAT788052P7C0667${it}",
            vehicleStatus = VehicleStatus.values().random(),
            adblueFuel = Fuel(type = FuelType.Adblue, quantity = (0..100).random(), capacity = 100),
            dieselFuel = Fuel(type = FuelType.Diesel, quantity = (0..100).random(), capacity = 100),
            chargerVoltage = 15f + (20 - 15f) * Random.nextFloat(),
            speed = 100f * Random.nextFloat(),
            waitTime = 60f * Random.nextFloat(),
            mileage = 10f + (50f - 10f) * Random.nextFloat(),
            location = "City-$it",
            lastUpdated = "2023-12-19T${(0..23).random()}:${(0..59).random()}:00Z"
        )
    }
}