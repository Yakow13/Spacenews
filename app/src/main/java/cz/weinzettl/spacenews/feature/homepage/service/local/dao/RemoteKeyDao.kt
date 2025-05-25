package cz.weinzettl.spacenews.feature.homepage.service.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.weinzettl.spacenews.feature.homepage.service.local.model.RemoteKey


@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE articleId = :articleId")
    suspend fun getRemoteKeyById(articleId: Int): RemoteKey?

    @Query("DELETE FROM remote_keys WHERE articleId = :articleId")
    suspend fun deleteKeyById(articleId: Int)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}