package cz.weinzettl.spacenews.sdk.storage.service.impl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import cz.weinzettl.spacenews.sdk.storage.data.KeyValueStorageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreService(
    private val context: Context
) : KeyValueStorageService {
    private val Context.dataStore by preferencesDataStore(name = SETTINGS)

    override fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }

    override suspend fun setBoolean(key: String, value: Boolean) {
        context.dataStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }

    companion object {
        private const val SETTINGS = "settings"
    }
}