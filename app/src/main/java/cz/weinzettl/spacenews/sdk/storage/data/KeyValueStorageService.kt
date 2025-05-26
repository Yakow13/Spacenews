package cz.weinzettl.spacenews.sdk.storage.data

import kotlinx.coroutines.flow.Flow

interface KeyValueStorageService {
    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean>
    suspend fun setBoolean(key: String, value: Boolean)
}