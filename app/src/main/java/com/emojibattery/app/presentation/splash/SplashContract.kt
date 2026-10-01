package com.emojibattery.app.presentation.splash

import com.emojibattery.app.core.mvi.UiEffect
import com.emojibattery.app.core.mvi.UiIntent
import com.emojibattery.app.core.mvi.UiState

data class SplashState(
    /** Loading progress in [0f, 1f]. */
    val progress: Float = 0f,
) : UiState

sealed interface SplashIntent : UiIntent {
    data object Start : SplashIntent
}

sealed interface SplashEffect : UiEffect {
    data object NavigateToHome : SplashEffect
}
