package com.emojibattery.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    val isFirstLaunch: Flow<Boolean>
    suspend fun setFirstLaunchCompleted()
}
