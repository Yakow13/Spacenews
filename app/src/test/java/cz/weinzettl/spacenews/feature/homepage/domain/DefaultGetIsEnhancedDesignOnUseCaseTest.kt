package cz.weinzettl.spacenews.feature.homepage.domain

import cz.weinzettl.spacenews.feature.homepage.domain.impl.DefaultGetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.sdk.BaseJUnitTest
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

class DefaultGetIsEnhancedDesignOnUseCaseTest : BaseJUnitTest() {
    private val repository = mockk<HomePageRepository>()

    @Test
    fun `When flag is returned Then result success is returned`() = runTest {
        coEvery { repository.isEnhancedDesignEnabled() } returns mockk()
        val useCase = provideUseCase()
        useCase().isSuccess shouldBe true
    }

    private fun provideUseCase() =
        DefaultGetIsEnhancedDesignOnUseCase(
            repository = repository,
            dispatchers = testDispatchers
        )
}