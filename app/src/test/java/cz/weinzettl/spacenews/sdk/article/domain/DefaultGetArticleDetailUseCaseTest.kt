package cz.weinzettl.spacenews.sdk.article.domain

import cz.weinzettl.spacenews.sdk.BaseJUnitTest
import cz.weinzettl.spacenews.sdk.article.domain.impl.DefaultGetArticleDetailUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test


class DefaultGetArticleDetailUseCaseTest : BaseJUnitTest() {
    private val repository = mockk<ArticleRepository>()

    @Test
    fun `When article detail is null Then result failure is returned`() = runTest {
        coEvery { repository.getArticleDetail(any()) } returns null
        val useCase = provideUseCase()
        useCase(10).isFailure shouldBe true
    }

    @Test
    fun `When article detail is not null Then result success is returned`() = runTest {
        coEvery { repository.getArticleDetail(any()) } returns mockk()
        val useCase = provideUseCase()
        useCase(10).isSuccess shouldBe true
    }

    private fun provideUseCase() =
        DefaultGetArticleDetailUseCase(
            repository = repository,
            dispatchers = testDispatchers
        )
}