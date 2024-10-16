package com.vecs.ui.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vecs.databinding.FragmentPlannerBinding

class PlannerFragment : Fragment() {

    private var _binding: FragmentPlannerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val plannerViewModel =
            ViewModelProvider(this).get(PlannerViewModel::class.java)

        _binding = FragmentPlannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPlanner
        plannerViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}