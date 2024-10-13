package com.vecs.ui.trace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vecs.data.adapters.TraceListAdapter
import com.vecs.data.models.Vehicle
import com.vecs.databinding.FragmentTraceListBinding
import com.vecs.ui.BaseFragment


class TraceListFragment : BaseFragment<TraceViewModel, FragmentTraceListBinding>(),TraceListAdapter.TraceListItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTraceListBinding.inflate(inflater, container, false)
        _viewmodel = ViewModelProvider(this).get(TraceViewModel::class.java)

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
    }

    override fun onItemClick(vehicle: Vehicle) {
        viewmodel.updateSelectedItem(vehicle)
    }

}