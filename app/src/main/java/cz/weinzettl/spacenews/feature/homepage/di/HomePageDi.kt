package cz.weinzettl.spacenews.feature.homepage.di

import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.data.impl.DefaultHomePageRepository
import cz.weinzettl.spacenews.feature.homepage.domain.GetArticleUrlUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.impl.DefaultGetArticleUrlUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.impl.DefaultGetArticlesUseCase
import cz.weinzettl.spacenews.feature.homepage.presentation.HomePageViewModel
import cz.weinzettl.spacenews.feature.homepage.service.remote.api.ArticleApiService
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val homePageDi = module {
    viewModelOf(::HomePageViewModel)

    factoryOf(::DefaultGetArticlesUseCase) bind GetArticlesUseCase::class

    factoryOf(::DefaultGetArticleUrlUseCase) bind GetArticleUrlUseCase::class

    factoryOf(::DefaultHomePageRepository) bind HomePageRepository::class

    factory<ArticleApiService> {
        get<Retrofit>().create(ArticleApiService::class.java)
    }
}