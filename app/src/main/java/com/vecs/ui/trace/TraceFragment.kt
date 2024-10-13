package com.vecs.ui.trace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vecs.databinding.FragmentTraceBinding
import com.vecs.ui.BaseFragment

class TraceFragment : BaseFragment<TraceViewModel, FragmentTraceBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTraceBinding.inflate(inflater, container, false)
        initUi()

        return binding.root
    }

    private fun initUi() {
        // load initial fragment
        val fragmentContainerView = binding.fragmentContainerView
        loadFragment(TraceListFragment(), fragmentContainerView.id, null);

        // init overview header

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}