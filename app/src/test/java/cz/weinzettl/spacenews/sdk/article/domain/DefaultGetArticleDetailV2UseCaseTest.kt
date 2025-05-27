package cz.weinzettl.spacenews.sdk.article.domain

import cz.weinzettl.spacenews.sdk.BaseJUnitTest
import cz.weinzettl.spacenews.sdk.article.domain.impl.DefaultGetArticleDetailV2UseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

class DefaultGetArticleDetailV2UseCaseTest : BaseJUnitTest() {

    private val repository = mockk<ArticleRepository>()

    @Test
    fun `When article detail is null Then result failure is returned`() = runTest {
        coEvery { repository.getArticleDetailV2(any()) } returns null
        val useCase = provideUseCase()
        useCase(99).isFailure shouldBe true
    }

    @Test
    fun `When article detail is not null Then result success is returned`() = runTest {
        coEvery { repository.getArticleDetailV2(any()) } returns mockk()
        val useCase = provideUseCase()
        useCase(99).isSuccess shouldBe true
    }

    private fun provideUseCase() =
        DefaultGetArticleDetailV2UseCase(
            repository = repository,
            dispatchers = testDispatchers
        )

}