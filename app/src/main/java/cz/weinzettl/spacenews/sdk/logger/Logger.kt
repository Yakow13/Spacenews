package cz.weinzettl.spacenews.sdk.logger

import timber.log.Timber

val logger = Logger

object Logger {

    fun verbose(message: String, vararg args: Any?) {
        Timber.v(message, *args)
    }

    fun verbose(t: Throwable, message: String, vararg args: Any?) {
        Timber.v(t, message, *args)
    }

    fun debug(message: String, vararg args: Any?) {
        Timber.d(message, *args)
    }

    fun debug(t: Throwable, message: String, vararg args: Any?) {
        Timber.d(t, message, *args)
    }

    fun info(message: String, vararg args: Any?) {
        Timber.i(message, *args)
    }

    fun info(t: Throwable, message: String, vararg args: Any?) {
        Timber.i(t, message, *args)
    }

    fun warning(message: String, vararg args: Any?) {
        Timber.w(message, *args)
    }

    fun warning(t: Throwable, message: String, vararg args: Any?) {
        Timber.w(t, message, *args)
    }

    fun error(t: Throwable, message: String, vararg args: Any?) {
        Timber.e(t, message, *args)
    }
}