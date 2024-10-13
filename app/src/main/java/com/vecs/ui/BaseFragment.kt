package com.vecs.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

/*
 * V -> ViewModel
 * B -> ViewBinding
 */
open class BaseFragment<V, B> : Fragment() {
    protected var _binding: B? = null
    protected val binding get() = _binding!!

    protected var _viewmodel: V? = null
    protected val viewmodel get() = _viewmodel!!

    protected fun loadFragment(fragment: Fragment, containerId: Int, bundle: Bundle? = null) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        if (bundle != null) {
            fragment.arguments = bundle
        }
        transaction.replace(containerId, fragment)
        transaction.commit()
    }
}