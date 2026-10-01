package com.emojibattery.app.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Inflates the ViewBinding [VB] and clears it in onDestroyView to avoid leaking the view.
 * Subclasses override [initView] and [observe] instead of onViewCreated.
 */
abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = checkNotNull(_binding) { "Binding accessed outside the view lifecycle" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    /** Set up views and listeners. */
    protected open fun initView() = Unit

    /** Collect ViewModel state/effect with [com.emojibattery.app.core.extension.collectWhenStarted]. */
    protected open fun observe() = Unit

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
