package cz.weinzettl.spacenews.feature.database.service

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.weinzettl.spacenews.feature.list.service.local.dao.ArticleDao
import cz.weinzettl.spacenews.feature.list.service.local.dao.RemoteKeyDao
import cz.weinzettl.spacenews.feature.list.service.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.list.service.local.model.RemoteKey

@Database(
    entities = [ArticleEntity::class, RemoteKey::class],
    version = 1
)
abstract class SpaceNewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        const val DATABASE_NAME = "space_news_db"

    }
}