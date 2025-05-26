package cz.weinzettl.spacenews.feature.homepage.data.impl

import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.sdk.storage.service.KeyValueStorageService
import kotlinx.coroutines.flow.Flow

class DefaultHomePageRepository(
    private val keyValueStorageService: KeyValueStorageService
) : HomePageRepository {
    override fun isEnhancedDesignEnabled(): Flow<Boolean> {
        return keyValueStorageService.getBoolean(ENHANCED_DESIGN_KEY, false)
    }

    override suspend fun setEnhancedDesignEnabled(enabled: Boolean) {
        keyValueStorageService.setBoolean(ENHANCED_DESIGN_KEY, enabled)
    }

    companion object {
        private const val ENHANCED_DESIGN_KEY = "enhanced_design_key"
    }
}