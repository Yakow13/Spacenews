package cz.weinzettl.spacenews.feature.homepage.presentation

import android.database.sqlite.SQLiteException
import androidx.annotation.StringRes
import cz.weinzettl.spacenews.R
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorMessageFactory {
    @StringRes
    fun getRes(throwable: Throwable): Int =
        when (throwable) {
            is UnknownHostException -> R.string.error_no_internet
            is ConnectException -> R.string.error_connect_exception
            is SocketTimeoutException -> R.string.error_socket_timeout
            is IOException -> R.string.error_io_exception
            is SQLiteException -> R.string.error_sqlite_exception
            else -> R.string.error_unexpected
        }
}