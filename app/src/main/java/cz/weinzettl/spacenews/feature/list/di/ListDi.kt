package cz.weinzettl.spacenews.feature.list.di

import cz.weinzettl.spacenews.feature.list.data.ListRepository
import cz.weinzettl.spacenews.feature.list.data.impl.DefaultListRepository
import cz.weinzettl.spacenews.feature.list.presentation.ListViewModel
import cz.weinzettl.spacenews.feature.list.service.remote.ArticleRemoteService
import cz.weinzettl.spacenews.feature.list.service.remote.api.ArticleApiService
import cz.weinzettl.spacenews.feature.list.service.remote.impl.DefaultArticleRemoteService
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val listDi = module {
    viewModelOf(::ListViewModel)

    factoryOf(::DefaultArticleRemoteService) bind ArticleRemoteService::class

    factoryOf(::DefaultListRepository) bind ListRepository::class

    factory<ArticleApiService> {
        get<Retrofit>().create(ArticleApiService::class.java)
    }
}