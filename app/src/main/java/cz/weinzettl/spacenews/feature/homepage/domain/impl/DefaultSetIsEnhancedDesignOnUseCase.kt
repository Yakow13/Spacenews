package cz.weinzettl.spacenews.feature.homepage.domain.impl

import cz.weinzettl.spacenews.feature.homepage.domain.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.domain.SetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.withContext

class DefaultSetIsEnhancedDesignOnUseCase(
    private val repository: HomePageRepository,
    private val dispatchers: Dispatchers,
) : SetIsEnhancedDesignOnUseCase {
    override suspend fun invoke(isEnabled: Boolean): Result<Unit> = withContext(dispatchers.io) {
        repository.runCatching {
            setEnhancedDesignEnabled(isEnabled)
        }.onFailure {
            logger.error(it, "Error while setting isEnhancedDesignEnabled")
        }
    }
}