package com.emojibattery.app.data.repository

import com.emojibattery.app.core.di.IoDispatcher
import com.emojibattery.app.data.local.AppPreferencesDataSource
import com.emojibattery.app.domain.repository.AppPreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AppPreferencesRepositoryImpl @Inject constructor(
    private val dataSource: AppPreferencesDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AppPreferencesRepository {

    override val isFirstLaunch: Flow<Boolean> = dataSource.isFirstLaunch.flowOn(ioDispatcher)

    override suspend fun setFirstLaunchCompleted() = withContext(ioDispatcher) {
        dataSource.setFirstLaunch(false)
    }
}
