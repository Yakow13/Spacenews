package cz.weinzettl.spacenews.feature.network.di

import cz.weinzettl.spacenews.feature.network.service.RetrofitService
import cz.weinzettl.spacenews.feature.network.service.impl.DefaultRetrofitService
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val networkDi = module {
    factoryOf(::DefaultRetrofitService) bind RetrofitService::class

    factory<Retrofit> {
        get<RetrofitService>().getClient()
    }
}