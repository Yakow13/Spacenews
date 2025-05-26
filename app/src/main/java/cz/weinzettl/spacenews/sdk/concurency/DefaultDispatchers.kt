package cz.weinzettl.spacenews.sdk.concurency

import kotlinx.coroutines.CoroutineDispatcher

class DefaultDispatchers : Dispatchers {
    override val io: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO
    override val default: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Default
    override val main: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main
}