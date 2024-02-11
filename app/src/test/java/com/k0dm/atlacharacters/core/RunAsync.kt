package com.k0dm.atlacharacters.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

internal class FakeRunAsync() : RunAsync {

    private var cacheResult: Any = Any()
    private var cacheUiBlock: (Any) -> Unit = {}
    var startCalledCount = 0

    fun <T : Any> start(
        coroutineScope: CoroutineScope,
        backgroundBlock: suspend () -> T,
        uiBlock: (T) -> Unit
    ) = runBlocking {
        startCalledCount++
        cacheResult = backgroundBlock.invoke()
        cacheUiBlock = uiBlock as (Any) -> Unit
    }

    fun pingResult() {
        cacheUiBlock.invoke(cacheResult)
    }
}