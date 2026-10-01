package com.emojibattery.app.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/** Collects [flow] while this owner is at least STARTED. */
fun <T> LifecycleOwner.collectWhenStarted(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { action(it) }
        }
    }
}

/** Collects [flow] tied to the Fragment's view lifecycle (use this in Fragments, not the Fragment itself). */
fun <T> Fragment.collectWhenStarted(flow: Flow<T>, action: suspend (T) -> Unit) =
    viewLifecycleOwner.collectWhenStarted(flow, action)
