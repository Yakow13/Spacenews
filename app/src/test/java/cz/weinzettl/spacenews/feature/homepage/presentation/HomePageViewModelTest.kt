package cz.weinzettl.spacenews.feature.homepage.presentation

import app.cash.turbine.test
import cz.weinzettl.spacenews.feature.homepage.domain.GetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.SetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiState
import cz.weinzettl.spacenews.sdk.BaseJUnitTest
import cz.weinzettl.spacenews.sdk.article.domain.GetArticlesUseCase
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class HomePageViewModelTest : BaseJUnitTest() {
    val getArticlesUseCase: GetArticlesUseCase = mockk()
    val getEnhancedDesignUseCase: GetIsEnhancedDesignOnUseCase = mockk()
    val setIsEnhancedDesignOnUseCase: SetIsEnhancedDesignOnUseCase = mockk()

    @Before
    fun setUp() {
        coEvery { getEnhancedDesignUseCase() } returns Result.success(flowOf(true))
    }

    @Test
    fun `initial state is Empty Then transitions to Loading Then Idle on successful article fetch`() =
        runTest {
            coEvery { getArticlesUseCase() } returns Result.success(flowOf(mockk()))

            val viewModel = provideViewModel()
            viewModel.uiState.test {

                awaitItem() shouldBe HomePageUiState.Empty

                awaitItem() shouldBe HomePageUiState.Loading

                awaitItem().shouldBeInstanceOf<HomePageUiState.Idle>()

                expectNoEvents()
            }
        }

    @Test
    fun `When enhanced design is enabled Then isEnhancedDesignOn is true`() =
        runTest {
            coEvery { getArticlesUseCase() } returns Result.success(flowOf(mockk()))

            val viewModel = provideViewModel()
            viewModel.uiState.test {

                skipItems(2)

                awaitItem().shouldBeInstanceOf<HomePageUiState.Idle> {
                    it.isEnhancedDesignOn shouldBe true
                }

                expectNoEvents()
            }
        }

    private fun provideViewModel() = HomePageViewModel(
        getArticlesUseCase,
        getEnhancedDesignUseCase,
        setIsEnhancedDesignOnUseCase,
        testDispatchers
    )
}