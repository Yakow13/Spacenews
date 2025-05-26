package cz.weinzettl.spacenews.app.di

import cz.weinzettl.spacenews.feature.article.di.articleDi
import cz.weinzettl.spacenews.feature.detail.di.detailDi
import cz.weinzettl.spacenews.feature.detailv2.di.detailV2Di
import cz.weinzettl.spacenews.feature.homepage.di.homePageDi
import cz.weinzettl.spacenews.sdk.concurency.di.concurrencyDi
import cz.weinzettl.spacenews.sdk.database.di.databaseDi
import cz.weinzettl.spacenews.sdk.network.di.networkDi
import cz.weinzettl.spacenews.sdk.storage.di.storageDi
import org.koin.core.module.Module

val spaceDi: List<Module> =
    listOf(
        articleDi,
        concurrencyDi,
        databaseDi,
        detailDi,
        detailV2Di,
        homePageDi,
        networkDi,
        storageDi,
    )