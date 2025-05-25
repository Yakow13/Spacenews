package cz.weinzettl.spacenews.feature.detailv2.di

import cz.weinzettl.spacenews.feature.detailv2.presentation.DetailV2ViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val detailV2Di = module {
    viewModelOf(::DetailV2ViewModel)
}