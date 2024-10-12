package com.vecs.ui.trace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vecs.databinding.FragmentTraceBinding

class TraceFragment : Fragment() {

    private var _binding: FragmentTraceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val traceViewModel =
            ViewModelProvider(this).get(TraceViewModel::class.java)

        _binding = FragmentTraceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTrace
        traceViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}