package com.emojibattery.app.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class AppPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val isFirstLaunch: Flow<Boolean> = dataStore.data.map { it[KEY_FIRST_LAUNCH] ?: true }

    suspend fun setFirstLaunch(value: Boolean) {
        dataStore.edit { it[KEY_FIRST_LAUNCH] = value }
    }

    private companion object {
        val KEY_FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }
}
