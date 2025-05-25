package cz.weinzettl.spacenews.feature.homepage.di

import cz.weinzettl.spacenews.feature.homepage.presentation.HomePageViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePageDi = module {
    viewModelOf(::HomePageViewModel)
}