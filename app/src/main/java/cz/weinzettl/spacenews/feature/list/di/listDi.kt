package cz.weinzettl.spacenews.feature.list.di

import cz.weinzettl.spacenews.feature.list.presentation.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val listDi = module {
    viewModelOf(::ListViewModel)
}