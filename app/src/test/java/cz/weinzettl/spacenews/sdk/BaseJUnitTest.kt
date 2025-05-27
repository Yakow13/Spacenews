package cz.weinzettl.spacenews.sdk

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

abstract class BaseJUnitTest {
    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)
    protected val testDispatchers = TestDispatchers(testDispatcher)

    protected fun runTest(
        dispatchers: TestDispatcher = testDispatcher,
        body: suspend TestScope.() -> Unit,
    ) {
        return runTest(context = dispatchers, testBody = body)
    }
}