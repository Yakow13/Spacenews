package cz.weinzettl.spacenews.feature.homepage.domain

interface SetIsEnhancedDesignOnUseCase {
    suspend operator fun invoke(isEnabled: Boolean): Result<Unit>
}