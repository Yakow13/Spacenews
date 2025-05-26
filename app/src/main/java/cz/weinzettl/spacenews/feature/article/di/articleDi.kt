package cz.weinzettl.spacenews.feature.article.di

import cz.weinzettl.spacenews.feature.article.data.ArticleRepository
import cz.weinzettl.spacenews.feature.article.data.impl.DefaultArticleRepository
import cz.weinzettl.spacenews.feature.article.domain.GetArticleDetailUseCase
import cz.weinzettl.spacenews.feature.article.domain.GetArticleDetailV2UseCase
import cz.weinzettl.spacenews.feature.article.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.feature.article.domain.impl.DefaultGetArticleDetailUseCase
import cz.weinzettl.spacenews.feature.article.domain.impl.DefaultGetArticleDetailV2UseCase
import cz.weinzettl.spacenews.feature.article.domain.impl.DefaultGetArticlesUseCase
import cz.weinzettl.spacenews.feature.article.service.remote.api.ArticleApiService
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