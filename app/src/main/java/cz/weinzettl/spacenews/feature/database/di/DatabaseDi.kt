package cz.weinzettl.spacenews.feature.database.di

import androidx.room.Room
import cz.weinzettl.spacenews.feature.database.service.SpaceNewsDatabase
import cz.weinzettl.spacenews.feature.list.service.local.dao.ArticleDao
import cz.weinzettl.spacenews.feature.list.service.local.dao.RemoteKeyDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseDi = module {
    single<SpaceNewsDatabase> {
        Room.databaseBuilder(
            androidContext(),
            SpaceNewsDatabase::class.java,
            SpaceNewsDatabase.DATABASE_NAME
        ).build()
    }

    factory<RemoteKeyDao> { get<SpaceNewsDatabase>().remoteKeyDao() }
    factory<ArticleDao> { get<SpaceNewsDatabase>().articleDao() }
}