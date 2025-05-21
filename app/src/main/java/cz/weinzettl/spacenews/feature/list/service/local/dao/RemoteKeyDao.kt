package cz.weinzettl.spacenews.feature.list.service.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.weinzettl.spacenews.feature.list.service.local.model.RemoteKey


@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE label = :label")
    suspend fun getRemoteKeyByLabel(label: String): RemoteKey?

    @Query("DELETE FROM remote_keys WHERE label = :label")
    suspend fun deleteByLabel(label: String)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}