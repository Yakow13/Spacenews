package cz.weinzettl.spacenews.app.di

import cz.weinzettl.spacenews.feature.list.di.listDi
import org.koin.core.module.Module

val allDi: List<Module> =
    listOf(
        listDi,
    )