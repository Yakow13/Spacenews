package cz.weinzettl.spacenews.app.di

import cz.weinzettl.spacenews.feature.detail.detailDi
import cz.weinzettl.spacenews.feature.homepage.di.homePageDi
import cz.weinzettl.spacenews.sdk.concurency.di.concurrencyDi
import cz.weinzettl.spacenews.sdk.database.di.databaseDi
import cz.weinzettl.spacenews.sdk.network.di.networkDi
import org.koin.core.module.Module

val spaceDi: List<Module> =
    listOf(
        concurrencyDi,
        databaseDi,
        detailDi,
        homePageDi,
        networkDi,
    )