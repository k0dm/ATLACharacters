package com.k0dm.atlacharacters.core

interface UiObservable<T : Any> : UpdateUiObserver<T>, UpdateUi<T>, Clear {

    abstract class Base<T : Any>(private val emptyUiState: T) : UiObservable<T> {

        protected var cachedUiState = emptyUiState
        private var cachedUiObserver: UiObserver<T> = UiObserver.Empty()

        override fun updateUi(uiState: T) {
            cachedUiState = uiState
            cachedUiObserver.updateUi(uiState)
        }

        override fun updateUiObserver(observer: UiObserver<T>) {
            cachedUiObserver = observer
            observer.updateUi(cachedUiState)
        }

        override fun clear() {
            cachedUiState = emptyUiState
        }
    }
}

interface UpdateUi<T : Any> {

    fun updateUi(uiState: T)
}

interface UiObserver<T : Any> : UpdateUi<T> {

    class Empty<T : Any> : UiObserver<T> {
        override fun updateUi(uiState: T) = Unit
    }
}

interface UpdateUiObserver<T : Any> {

    fun updateUiObserver(observer: UiObserver<T>)
}