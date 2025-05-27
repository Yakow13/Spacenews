package cz.weinzettl.spacenews.sdk.article.domain

import cz.weinzettl.spacenews.sdk.BaseJUnitTest
import cz.weinzettl.spacenews.sdk.article.domain.impl.DefaultGetArticlesUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import java.io.IOException

class DefaultGetArticlesUseCaseTest : BaseJUnitTest() {
    private val repository = mockk<ArticleRepository>()

    @Test
    fun `When repository throws exception Then result failure is returned`() = runTest {
        coEvery { repository.getArticlesStream() } throws IOException()
        val useCase = provideUseCase()
        useCase().isFailure shouldBe true
    }

    @Test
    fun `When article is not null Then result success is returned`() = runTest {
        coEvery { repository.getArticlesStream() } returns flowOf(mockk())
        val useCase = provideUseCase()
        useCase().isSuccess shouldBe true
    }

    private fun provideUseCase() =
        DefaultGetArticlesUseCase(
            articleRepository = repository,
            dispatchers = testDispatchers
        )
}