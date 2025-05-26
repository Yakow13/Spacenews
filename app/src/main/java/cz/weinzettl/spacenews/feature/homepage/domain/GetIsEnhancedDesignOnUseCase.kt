package cz.weinzettl.spacenews.feature.homepage.domain

import kotlinx.coroutines.flow.Flow

interface GetIsEnhancedDesignOnUseCase {
    operator fun invoke(): Result<Flow<Boolean>>
}