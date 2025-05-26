package cz.weinzettl.spacenews.feature.homepage.domain.impl

import cz.weinzettl.spacenews.feature.homepage.domain.GetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.HomePageRepository
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultGetIsEnhancedDesignOnUseCase(
    private val repository: HomePageRepository,
    private val dispatchers: Dispatchers,
) : GetIsEnhancedDesignOnUseCase {
    override fun invoke(): Result<Flow<Boolean>> =
        repository.runCatching {
            isEnhancedDesignEnabled()
                .flowOn(dispatchers.io)
        }.onFailure {
            logger.error(it, "Error while getting isEnhancedDesignEnabled")
        }

}


