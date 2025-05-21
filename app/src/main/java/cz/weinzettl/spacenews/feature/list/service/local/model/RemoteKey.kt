package cz.weinzettl.spacenews.feature.list.service.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    val label: String,
    val nextKey: String?
)