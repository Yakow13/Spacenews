package cz.weinzettl.spacenews.sdk.viewmodel

import kotlinx.coroutines.flow.SharingStarted

@Suppress("FunctionName")
fun SharingStarted.Companion.WhileSubscribed5000(): SharingStarted =
    WhileSubscribed(5000L)