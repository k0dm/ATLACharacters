package com.k0dm.atlacharacters.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface Representative<T : Any> {

    fun startGettingUpdates(observer: UiObserver<T>)

    fun stopGettingUpdates()

    abstract class BaseRepresentative<T : Any>(private val runAsync: RunAsync) : Representative<T> {

        private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

        protected fun <T : Any> runAsync(
            backgroundBlock: suspend () -> T, uiBLock: (T) -> Unit
        ) {
            runAsync.start(coroutineScope, backgroundBlock, uiBLock)
        }
    }
}