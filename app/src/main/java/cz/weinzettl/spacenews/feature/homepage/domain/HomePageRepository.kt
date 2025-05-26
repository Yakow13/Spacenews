package cz.weinzettl.spacenews.feature.homepage.domain

import kotlinx.coroutines.flow.Flow

interface HomePageRepository {
    fun isEnhancedDesignEnabled(): Flow<Boolean>

    suspend fun setEnhancedDesignEnabled(enabled: Boolean)
}