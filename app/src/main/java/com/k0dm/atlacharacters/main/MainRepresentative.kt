package com.k0dm.atlacharacters.main

import com.k0dm.atlacharacters.characters.presentation.CharactersScreen
import com.k0dm.atlacharacters.core.Representative
import com.k0dm.atlacharacters.core.UiObserver

interface MainRepresentative: Representative<Screen>{

    fun init(isFirstRun: Boolean)

    fun navigate(screen: Screen)

    fun notifyObserved()

    class Base(private val navigation: Navigation.Mutable): MainRepresentative{

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) navigate(CharactersScreen)
        }

        override fun navigate(screen: Screen) {
            navigation.updateUi(screen)
        }

        override fun notifyObserved() {
            navigation.clear()
        }

        override fun startGettingUpdates(observer: UiObserver<Screen>) {
            navigation.updateUiObserver(observer)
        }

        override fun stopGettingUpdates() {
            navigation.updateUiObserver(NavigationObserver.Empty)
        }
    }
}
