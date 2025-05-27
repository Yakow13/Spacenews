package cz.weinzettl.spacenews.sdk.article.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    val articleId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)