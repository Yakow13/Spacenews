package cz.weinzettl.spacenews.sdk.article.di


import cz.weinzettl.spacenews.sdk.article.data.remote.api.ArticleApiService
import cz.weinzettl.spacenews.sdk.article.data.repository.impl.DefaultArticleRepository
import cz.weinzettl.spacenews.sdk.article.domain.ArticleRepository
import cz.weinzettl.spacenews.sdk.article.domain.GetArticleDetailUseCase
import cz.weinzettl.spacenews.sdk.article.domain.GetArticleDetailV2UseCase
import cz.weinzettl.spacenews.sdk.article.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.sdk.article.domain.impl.DefaultGetArticleDetailUseCase
import cz.weinzettl.spacenews.sdk.article.domain.impl.DefaultGetArticleDetailV2UseCase
import cz.weinzettl.spacenews.sdk.article.domain.impl.DefaultGetArticlesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val articleDi = module {
    factoryOf(::DefaultGetArticlesUseCase) bind GetArticlesUseCase::class

    factoryOf(::DefaultGetArticleDetailUseCase) bind GetArticleDetailUseCase::class

    factoryOf(::DefaultGetArticleDetailV2UseCase) bind GetArticleDetailV2UseCase::class

    factoryOf(::DefaultArticleRepository) bind ArticleRepository::class

    factory<ArticleApiService> {
        get<Retrofit>().create(ArticleApiService::class.java)
    }
}