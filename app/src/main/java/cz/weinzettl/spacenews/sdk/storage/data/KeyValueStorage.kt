package cz.weinzettl.spacenews.sdk.storage.data

import kotlinx.coroutines.flow.Flow

interface KeyValueStorage {
    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean>
    suspend fun setBoolean(key: String, value: Boolean)
}