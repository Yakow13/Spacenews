package cz.weinzettl.spacenews.sdk

import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatchers(testDispatcher: TestDispatcher) : Dispatchers {
    override val io: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
}