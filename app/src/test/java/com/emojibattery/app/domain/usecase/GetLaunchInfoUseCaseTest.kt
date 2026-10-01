package com.emojibattery.app.domain.usecase

import com.emojibattery.app.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GetLaunchInfoUseCaseTest {

    private class FakeRepository : AppPreferencesRepository {
        override val isFirstLaunch = MutableStateFlow(true)
        override suspend fun setFirstLaunchCompleted() {
            isFirstLaunch.value = false
        }
    }

    @Test
    fun `first call reports first launch, later calls do not`() = runTest {
        val useCase = GetLaunchInfoUseCase(FakeRepository())

        assertTrue(useCase().isFirstLaunch)
        assertFalse(useCase().isFirstLaunch)
    }
}
