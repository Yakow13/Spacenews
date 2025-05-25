package cz.weinzettl.spacenews.feature.detail.di

import cz.weinzettl.spacenews.feature.detail.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val detailDi = module {
    viewModelOf(::DetailViewModel)
}