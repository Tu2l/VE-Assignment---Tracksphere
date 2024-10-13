package com.vecs.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.vecs.R
import com.vecs.data.models.Fuel
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus
import com.vecs.databinding.LayoutTraceListItemBinding

class TraceListAdapter : RecyclerView.Adapter<TraceListAdapter.ViewHolder>() {
    var data = listOf<Vehicle>()
        public set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(private val binding: LayoutTraceListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vehicle: Vehicle) {
            // setting vehicle ids
            binding.vehicleIdPrimary.text = vehicle.primaryVehicleId
            binding.vehicleIdSecondary.text = vehicle.secondaryVehicleId

            // determining and setting vehicle status and its appropriate views
            var statusDrawable = getStatusDrawable(
                binding.root,
                R.drawable.drawable_rounded_corner_with_running_color
            )
            var statusText = binding.root.context.getString(R.string.running)
            var secondaryStatusText = "${vehicle.speed}Kmph"

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
                    secondaryStatusText = "${vehicle.waitTime}hrs"
                }

                VehicleStatus.Idle -> {
                    statusDrawable =
                        getStatusDrawable(
                            binding.root,
                            R.drawable.drawable_rounded_corner_with_idle_color
                        )
                    statusText = binding.root.context.getString(R.string.idle)
                    secondaryStatusText = "${vehicle.waitTime}hrs"
                }

                VehicleStatus.Offline -> {
                    statusDrawable =
                        getStatusDrawable(
                            binding.root,
                            R.drawable.drawable_rounded_corner_with_offline_color
                        )
                    statusText = binding.root.context.getString(R.string.offline)
                    secondaryStatusText = "${vehicle.waitTime}hrs"
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

            val chargerVoltage = "${vehicle.chargerVoltage} V"
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutTraceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = data[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}