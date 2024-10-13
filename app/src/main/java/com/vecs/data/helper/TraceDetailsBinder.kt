package com.vecs.data.helper

import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.vecs.R
import com.vecs.data.models.Fuel
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus
import com.vecs.databinding.LayoutTraceDetailsBinding

class TraceDetailsBinder(
    private val binding: LayoutTraceDetailsBinding,
    private val vehicle: Vehicle
) {
    fun bind() {
        bindPrimaryDetails()
        bindSecondaryDetails()
        bindBtnClicks()
    }

    private fun bindBtnClicks() {
        binding.detailsButton.setOnClickListener { v ->
            // show additional hardcoded details
            binding.additionalDetails.root.visibility = View.VISIBLE
            v.visibility = View.GONE
            bindPrimaryDetails() // due to a bug reassignment/binding is required to keep the view consistent
            Toast.makeText(
                binding.root.context,
                "Following additional details are hardcoded",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.needHelpBtn.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "Need Help button clicked, no action specified",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun bindSecondaryDetails() {
        val progressLayout = binding.circularProgressLayout
        progressLayout.progressBarAdblue.progress = vehicle.adblueFuel.quantity
        progressLayout.progressBarAdblue.max = vehicle.adblueFuel.capacity
        progressLayout.progressBarDiesel.progress = vehicle.dieselFuel.quantity
        progressLayout.progressBarDiesel.max = vehicle.dieselFuel.capacity

        val adblueProgessText = "${vehicle.adblueFuel.quantity}/${vehicle.adblueFuel.capacity}"
        progressLayout.progressTextAdblue.text = adblueProgessText

        val dieselProgressText = "${vehicle.dieselFuel.quantity}/${vehicle.dieselFuel.capacity}"
        progressLayout.progressTextDiesel.text = dieselProgressText

        binding.locationText.text = vehicle.location
        val lastupdatedText = "Last Updated: ${vehicle.lastUpdated}"
        binding.lastupdatedText.text = lastupdatedText

        val milageText = "${formatDigit(vehicle.mileage)}Kmpl"
        binding.milageText.text = milageText
    }

    private fun bindPrimaryDetails() {
        // setting vehicle ids
        binding.vehicleIdPrimary.text = vehicle.primaryVehicleId
        binding.vehicleIdSecondary.text = vehicle.secondaryVehicleId

        // determining and setting vehicle status and its appropriate views
        var statusDrawable = getStatusDrawable(
            binding.root,
            R.drawable.drawable_rounded_corner_with_running_color
        )
        var statusText = binding.root.context.getString(R.string.running)
        var secondaryStatusText = "${formatDigit(vehicle.speed)}Kmph"

        when (vehicle.vehicleStatus) {
            VehicleStatus.Running -> {
                // no need to assign value here, initial values are already assigned for running status
            }

            VehicleStatus.Stopped -> {
                statusDrawable =
                    getStatusDrawable(
                        binding.root,
                        R.drawable.drawable_rounded_corner_with_stopped_color
                    )
                statusText = binding.root.context.getString(R.string.halt)
                secondaryStatusText = "${formatDigit(vehicle.waitTime)}hrs"
            }

            VehicleStatus.Idle -> {
                statusDrawable =
                    getStatusDrawable(
                        binding.root,
                        R.drawable.drawable_rounded_corner_with_idle_color
                    )
                statusText = binding.root.context.getString(R.string.idle)
                secondaryStatusText = "${formatDigit(vehicle.waitTime)}hrs"
            }

            VehicleStatus.Offline -> {
                statusDrawable =
                    getStatusDrawable(
                        binding.root,
                        R.drawable.drawable_rounded_corner_with_offline_color
                    )
                statusText = binding.root.context.getString(R.string.offline)
                secondaryStatusText = "${formatDigit(vehicle.waitTime)}hrs"
            }
        }

        binding.vehicleStatusIndicator.background = statusDrawable
        binding.statusTextLayout.background = statusDrawable
        binding.statusTextPrimary.text = statusText
        binding.statusTextSecondary.text = secondaryStatusText

        // setting fuel indicators

        binding.adblueFuelQuantityIcon.setImageResource(
            if (isFuelLow(vehicle.adblueFuel))
                R.drawable.ic_red_adblue_quantity else R.drawable.ic_adblue_blue_quantity
        )

        binding.dieselQuantityIcon.setImageResource(
            if (isFuelLow(vehicle.dieselFuel))
                R.drawable.ic_red_fuel_quantity else R.drawable.ic_green_fuel_quantity
        )

        val chargerVoltage = "${formatDigit(vehicle.chargerVoltage)} V"
        binding.chargerVoltageText.text = chargerVoltage
    }

    //load background drawable
    private fun getStatusDrawable(view: View, drawableId: Int) =
        AppCompatResources.getDrawable(view.context, drawableId)

    /*
     * If the fuel is less than equal to one fourth of its capacity then this method returns true
     */
    private fun isFuelLow(fuel: Fuel): Boolean {
        return fuel.quantity <= fuel.capacity / 4
    }

    private fun formatDigit(digit: Number): String {
        return String.format("%.2f", digit)
    }

}