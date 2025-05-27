package cz.weinzettl.spacenews.sdk.storage.di

import cz.weinzettl.spacenews.sdk.storage.data.KeyValueStorage
import cz.weinzettl.spacenews.sdk.storage.data.impl.DataStoreStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storageDi = module {
    singleOf(::DataStoreStorage) bind KeyValueStorage::class
}