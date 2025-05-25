package cz.weinzettl.spacenews.feature.homepage.domain

interface GetArticleUrlUseCase {
    suspend operator fun invoke(articleId: Int): Result<String>
}