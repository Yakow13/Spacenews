package cz.weinzettl.spacenews.app

import android.app.Application
import cz.weinzettl.spacenews.app.di.allDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SpaceNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SpaceNewsApp)
            modules(allDi)
        }
    }
}