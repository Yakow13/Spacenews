package cz.weinzettl.spacenews.feature.detail

import cz.weinzettl.spacenews.feature.detail.presentation.DetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val detailDi = module {
    viewModelOf(::DetailViewModel)
}