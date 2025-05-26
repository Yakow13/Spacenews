package cz.weinzettl.spacenews.sdk.concurency.di

import cz.weinzettl.spacenews.sdk.concurency.DefaultDispatchers
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val concurrencyDi = module {
    singleOf(::DefaultDispatchers) bind Dispatchers::class
}