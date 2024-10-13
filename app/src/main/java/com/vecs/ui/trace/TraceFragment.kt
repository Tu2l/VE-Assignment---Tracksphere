package com.vecs.ui.trace

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.vecs.R
import com.vecs.databinding.FragmentTraceBinding
import com.vecs.databinding.LayoutVehicleToolboxBinding
import com.vecs.ui.BaseFragment

private val TRACE_LIST_FRAGMENT = 1
private val TRACE_DETAILS_FRAGMENT = 2

class TraceFragment : BaseFragment<TraceViewModel, FragmentTraceBinding>() {

    private val sharedViewModel: TraceViewModel by activityViewModels()
    private var currentFragmentId: Int = TRACE_LIST_FRAGMENT


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTraceBinding.inflate(inflater, container, false)
        _viewmodel = sharedViewModel

        initUi()

        return binding.root
    }

    private fun initUi() {
        // load initial fragment
        loadFragment(TraceListFragment(), R.id.fragment_container_view, null);
        currentFragmentId = TRACE_DETAILS_FRAGMENT;

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
            if (currentFragmentId == TRACE_DETAILS_FRAGMENT) {
                loadFragment(TraceListFragment(), R.id.fragment_container_view, null)
                currentFragmentId = TRACE_LIST_FRAGMENT
                setSearchBoxState(toolboxBinding, true)
                viewmodel.updateSelectedItem("")
            }

            Toast.makeText(context, "Back button clicked", Toast.LENGTH_SHORT).show()
        }

        // filter button
        toolboxBinding.filterBtn.setOnClickListener {
            Toast.makeText(
                context,
                "Filter button clicked, no behaviour specified",
                Toast.LENGTH_LONG
            ).show()
        }

        viewmodel.selectedItem.observe(viewLifecycleOwner) { vehicleId ->
            val enable = TextUtils.isEmpty(vehicleId)
            toolboxBinding.searchVehicleInput.setText(vehicleId)

            if (!enable) {
                setSearchBoxState(toolboxBinding, false)
                //navigate to trace details fragment
                val bundle = Bundle()
                bundle.putString(ARG_VEHICLE_ID, vehicleId)

                loadFragment(TraceDetailsFragment(), R.id.fragment_container_view, bundle)
                currentFragmentId = TRACE_DETAILS_FRAGMENT
            }
        }

        // search text change listener
        toolboxBinding.searchVehicleInput.addTextChangedListener {
            val query = it.toString()
            viewmodel.updateSearchQuery(query)
        }
    }

    private fun setSearchBoxState(
        toolboxBinding: LayoutVehicleToolboxBinding,
        enable: Boolean
    ) {
        toolboxBinding.searchVehicleInput.inputType =
            if (enable) InputType.TYPE_CLASS_TEXT else InputType.TYPE_NULL
        toolboxBinding.searchVehicleInput.isFocusable = enable
        toolboxBinding.searchVehicleInput.isFocusableInTouchMode = enable
    }

    private fun initOverviewHeader() {
        val headerBinding = binding.vehicleOverviewAndToolboxLayout
            .vehicleCountHeader

        viewmodel.loadRunningVehiclesCount().observe(viewLifecycleOwner) {
            headerBinding.runningVehicleCount.text = it.toString()
        }

        viewmodel.loadStoppedVehiclesCount().observe(viewLifecycleOwner) {
            headerBinding.stoppedVehicleCount.text = it.toString()
        }

        viewmodel.loadIdleVehiclesCount().observe(viewLifecycleOwner) {
            headerBinding.idleVehicleCount.text = it.toString()
        }

        viewmodel.loadOfflineVehiclesCount().observe(viewLifecycleOwner) {
            headerBinding.offlineVehicleCount.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}