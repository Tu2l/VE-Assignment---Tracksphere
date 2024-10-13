package com.vecs.ui.trace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.vecs.data.helper.TraceDetailsBinder
import com.vecs.databinding.FragmentTraceDetailsBinding
import com.vecs.ui.BaseFragment

const val ARG_VEHICLE_ID = "vehicleId"

class TraceDetailsFragment : BaseFragment<TraceViewModel, FragmentTraceDetailsBinding>() {
    private var vehicleId: String? = null
    private val sharedViewModel: TraceViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vehicleId = it.getString(ARG_VEHICLE_ID)
        }

        assert(vehicleId != null) // if vehicle id is null then something is wrong
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewmodel = sharedViewModel
        _binding = FragmentTraceDetailsBinding.inflate(inflater, container, false)

        initUi()

        return binding.root
    }

    private fun initUi() {
        viewmodel.loadVehicleById(vehicleId!!).observe(this) { vehicle ->
            TraceDetailsBinder(
                binding.traceDetails,
                vehicle
            ).bind()
        }
    }

}