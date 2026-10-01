package com.emojibattery.app.domain.usecase

import com.emojibattery.app.domain.model.LaunchInfo
import com.emojibattery.app.domain.repository.AppPreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/**
 * Prepares the app on start-up and tells the caller whether this is the first launch.
 * Marks the first launch as done so the next call returns `false`.
 */
class GetLaunchInfoUseCase @Inject constructor(
    private val repository: AppPreferencesRepository,
) {
    suspend operator fun invoke(): LaunchInfo {
        val isFirstLaunch = repository.isFirstLaunch.first()
        if (isFirstLaunch) repository.setFirstLaunchCompleted()
        return LaunchInfo(isFirstLaunch = isFirstLaunch)
    }
}
