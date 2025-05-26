package cz.weinzettl.spacenews.feature.homepage.di

import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.data.impl.DefaultHomePageRepository
import cz.weinzettl.spacenews.feature.homepage.domain.GetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.SetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.impl.DefaultGetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.impl.DefaultSetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.presentation.HomePageViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homePageDi = module {
    viewModelOf(::HomePageViewModel)

    factoryOf(::DefaultGetIsEnhancedDesignOnUseCase) bind GetIsEnhancedDesignOnUseCase::class

    factoryOf(::DefaultSetIsEnhancedDesignOnUseCase) bind SetIsEnhancedDesignOnUseCase::class

    factoryOf(::DefaultHomePageRepository) bind HomePageRepository::class
}