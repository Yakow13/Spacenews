package cz.weinzettl.spacenews.app.di

import cz.weinzettl.spacenews.feature.database.di.databaseDi
import cz.weinzettl.spacenews.feature.list.di.listDi
import cz.weinzettl.spacenews.feature.network.di.networkDi
import org.koin.core.module.Module

val spaceDi: List<Module> =
    listOf(
        databaseDi,
        listDi,
        networkDi,
    )