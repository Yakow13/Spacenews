package cz.weinzettl.spacenews.feature.homepage.domain

import cz.weinzettl.spacenews.feature.homepage.domain.impl.DefaultSetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.sdk.BaseJUnitTest
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class DefaultSetIsEnhancedDesignOnUseCaseTest : BaseJUnitTest() {
    private val repository = mockk<HomePageRepository>(relaxed = true)

    @Test
    fun `When flag is set true Then the repository should be called with true`() = runTest {
        val useCase = provideUseCase()
        useCase(true)

        coVerify { repository.setEnhancedDesignEnabled(true) }
    }

    @Test
    fun `When flag is set false Then the repository should be called with false`() = runTest {
        val useCase = provideUseCase()
        useCase(false)

        coVerify { repository.setEnhancedDesignEnabled(false) }
    }


    private fun provideUseCase() =
        DefaultSetIsEnhancedDesignOnUseCase(
            repository = repository,
            dispatchers = testDispatchers
        )
}