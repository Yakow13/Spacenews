package cz.weinzettl.spacenews.feature.homepage.domain.impl

import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.domain.GetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.flow.Flow

class DefaultGetIsEnhancedDesignOnUseCase(
    private val repository: HomePageRepository,
) : GetIsEnhancedDesignOnUseCase {
    override fun invoke(): Result<Flow<Boolean>> =
        repository.runCatching {
            isEnhancedDesignEnabled()
        }.onFailure {
            logger.error(it, "Error while getting isEnhancedDesignEnabled")
        }

}


