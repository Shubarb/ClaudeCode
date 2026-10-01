package com.emojibattery.app.presentation.splash

import androidx.lifecycle.viewModelScope
import com.emojibattery.app.core.mvi.MviViewModel
import com.emojibattery.app.domain.usecase.GetLaunchInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLaunchInfo: GetLaunchInfoUseCase,
) : MviViewModel<SplashState, SplashIntent, SplashEffect>(SplashState()) {

    private var started = false

    override fun handleIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.Start -> start()
        }
    }

    private fun start() {
        if (started) return
        started = true
        viewModelScope.launch {
            // App init runs while the progress bar fills, so the splash shows for at least
            // MIN_DURATION_MS and as long as init needs.
            val launchInfo = async { getLaunchInfo() }
            val steps = (MIN_DURATION_MS / TICK_MS).toInt()
            repeat(steps) { step ->
                delay(TICK_MS)
                setState { copy(progress = (step + 1) * 100 / steps) }
            }
            launchInfo.await()
            // TODO: route to onboarding when launchInfo.isFirstLaunch once that screen exists.
            sendEffect(SplashEffect.NavigateToHome)
        }
    }

    private companion object {
        const val MIN_DURATION_MS = 2_000L
        const val TICK_MS = 20L
    }
}
