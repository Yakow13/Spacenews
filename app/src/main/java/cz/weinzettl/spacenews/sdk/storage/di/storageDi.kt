package cz.weinzettl.spacenews.sdk.storage.di

import cz.weinzettl.spacenews.sdk.storage.service.KeyValueStorageService
import cz.weinzettl.spacenews.sdk.storage.service.impl.DataStoreService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storageDi = module {
    singleOf(::DataStoreService) bind KeyValueStorageService::class
}