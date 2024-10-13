package com.vecs.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.vecs.R
import com.vecs.data.models.Fuel
import com.vecs.data.models.Vehicle
import com.vecs.data.models.VehicleStatus
import com.vecs.databinding.LayoutTraceListItemBinding

class TraceListAdapter(private val clickListener: TraceListItemClickListener) :
    RecyclerView.Adapter<TraceListAdapter.ViewHolder>(),
    Filterable {

    private var filteredData: List<Vehicle> = listOf()

    var data = listOf<Vehicle>()
        set(value) {
            field = value
            filteredData = value
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutTraceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = filteredData[position]
        holder.bind(vehicle)

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(vehicle)
        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val query = constraint?.toString()?.toLowerCase()

                filteredData = if (query.isNullOrEmpty()) {
                    data // If query is empty, show all items
                } else {
                    data.filter { item ->
                        item.primaryVehicleId.toLowerCase().contains(query) // Filter based on name
                    }
                }

                filterResults.values = filteredData
                filterResults.count = filteredData.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as? List<Vehicle> ?: listOf()
                notifyDataSetChanged()
            }
        }
    }

    interface TraceListItemClickListener {
        fun onItemClick(vehicle: Vehicle)
    }
}