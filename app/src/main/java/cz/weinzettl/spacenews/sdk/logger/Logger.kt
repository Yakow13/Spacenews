package cz.weinzettl.spacenews.sdk.logger

import timber.log.Timber

val logger = Logger

object Logger {
    fun error(t: Throwable, message: String, vararg args: Any?) {
        Timber.e(t, message, *args)
    }
}