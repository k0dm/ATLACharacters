package com.k0dm.atlacharacters.core

interface Representative<T: Any> {

    fun startGettingUpdates(observer: UiObserver<T>)

    fun stopGettingUpdates()
}