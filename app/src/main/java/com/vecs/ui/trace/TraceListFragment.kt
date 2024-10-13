package com.vecs.ui.trace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vecs.data.adapters.TraceListAdapter
import com.vecs.data.models.Vehicle
import com.vecs.databinding.FragmentTraceListBinding
import com.vecs.ui.BaseFragment


class TraceListFragment : BaseFragment<TraceViewModel, FragmentTraceListBinding>(),
    TraceListAdapter.TraceListItemClickListener {
    private val sharedViewModel: TraceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTraceListBinding.inflate(inflater, container, false)
        _viewmodel = sharedViewModel

        initUi()
        return binding.root
    }

    private fun initUi() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TraceListAdapter(this)
        recyclerView.adapter = adapter

        viewmodel.loadVehicles().observe(viewLifecycleOwner) {
            adapter.data = it
        }

        viewmodel.searchQuery.observe(viewLifecycleOwner) {
            adapter.filter.filter(it)
        }
    }

    override fun onItemClick(vehicle: Vehicle) {
        viewmodel.updateSelectedItem(vehicle.primaryVehicleId)
    }

}