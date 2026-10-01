package com.emojibattery.app.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/** Inflates the ViewBinding [VB] and exposes it as [binding]. */
abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: (LayoutInflater) -> VB,
) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB get() = checkNotNull(_binding) { "Binding accessed before onCreate" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        observe()
    }

    /** Set up views and listeners. */
    protected open fun initView() = Unit

    /** Collect ViewModel state/effect. */
    protected open fun observe() = Unit

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
