package com.vecs.ui.trace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vecs.databinding.FragmentTraceBinding
import com.vecs.ui.BaseFragment

class TraceFragment : BaseFragment<TraceViewModel, FragmentTraceBinding>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTraceBinding.inflate(inflater, container, false)
        _viewmodel = ViewModelProvider(this).get(TraceViewModel::class.java)

        initUi()

        return binding.root
    }

    private fun initUi() {
        // load initial fragment
        val fragmentContainerView = binding.fragmentContainerView
        loadFragment(TraceListFragment(), fragmentContainerView.id, null);

        // init overview header
        initOverviewHeader()

        // init toolbox header
        initToolboxHeader()
    }

    private fun initToolboxHeader() {
        val toolboxBinding = binding.vehicleOverviewAndToolboxLayout
            .vehicleToolboxHeader

        // back button
        toolboxBinding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
            Toast.makeText(context,"Back button clicked", Toast.LENGTH_SHORT).show()
        }

        // filter button
        toolboxBinding.filterBtn.setOnClickListener {
            Toast.makeText(context,"Filter button clicked, no behaviour specified", Toast.LENGTH_LONG).show()
        }

        viewmodel.selectedItem.observe(viewLifecycleOwner) { vehicle ->
            view?.post {
                toolboxBinding.searchVehicleInput.setText(vehicle.primaryVehicleId)
            }
        }
    }

    private fun initOverviewHeader() {
        val headerBinding = binding.vehicleOverviewAndToolboxLayout
            .vehicleCountHeader

        viewmodel.loadRunningVehiclesCount().observe(this) {
            headerBinding.runningVehicleCount.text = it.toString()
        }

        viewmodel.loadStoppedVehiclesCount().observe(this) {
            headerBinding.stoppedVehicleCount.text = it.toString()
        }

        viewmodel.loadIdleVehiclesCount().observe(this) {
            headerBinding.idleVehicleCount.text = it.toString()
        }

        viewmodel.loadOfflineVehiclesCount().observe(this) {
            headerBinding.offlineVehicleCount.text = it.toString()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}