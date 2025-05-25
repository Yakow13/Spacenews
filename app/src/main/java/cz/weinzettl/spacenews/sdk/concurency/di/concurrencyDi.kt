package cz.weinzettl.spacenews.sdk.concurency.di

import cz.weinzettl.spacenews.sdk.concurency.service.Dispatchers
import cz.weinzettl.spacenews.sdk.concurency.service.impl.DefaultDispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val concurrencyDi = module {
    singleOf(::DefaultDispatchers) bind Dispatchers::class
}