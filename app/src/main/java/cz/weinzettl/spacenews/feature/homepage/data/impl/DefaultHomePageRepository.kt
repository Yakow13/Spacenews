package cz.weinzettl.spacenews.feature.homepage.data.impl

import cz.weinzettl.spacenews.feature.homepage.domain.HomePageRepository
import cz.weinzettl.spacenews.sdk.storage.data.KeyValueStorage
import kotlinx.coroutines.flow.Flow

class DefaultHomePageRepository(
    private val keyValueStorage: KeyValueStorage
) : HomePageRepository {
    override fun isEnhancedDesignEnabled(): Flow<Boolean> {
        return keyValueStorage.getBoolean(ENHANCED_DESIGN_KEY, false)
    }

    override suspend fun setEnhancedDesignEnabled(enabled: Boolean) {
        keyValueStorage.setBoolean(ENHANCED_DESIGN_KEY, enabled)
    }

    companion object {
        private const val ENHANCED_DESIGN_KEY = "enhanced_design_key"
    }
}