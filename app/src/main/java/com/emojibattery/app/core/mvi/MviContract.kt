package com.emojibattery.app.core.mvi

/** Immutable snapshot of everything a screen renders. */
interface UiState

/** A user action or lifecycle event sent from the UI to the ViewModel. */
interface UiIntent

/** A one-off event the UI consumes once (navigation, toast, dialog...). */
interface UiEffect
