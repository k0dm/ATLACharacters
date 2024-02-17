package com.k0dm.atlacharacters.main

import com.k0dm.atlacharacters.characters.presentation.CharactersFragment
import com.k0dm.atlacharacters.core.Representative
import com.k0dm.atlacharacters.core.UiObserver
import com.k0dm.atlacharacters.favorites.presentation.FavoritesFragment

interface MainRepresentative: Representative<Screen>{

    fun init(isFirstRun: Boolean)

    fun navigate(screen: Screen)

    fun navigateToCharacters()

    fun navigateToFavorites()

    fun notifyObserved()

    class Base(private val navigation: Navigation.Mutable): MainRepresentative{

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun){
             //   navigate(CharactersScreen)
                navigateToCharacters()
            }
        }

        override fun navigateToCharacters() {
            navigation.updateUi(Screen.ShowAndHide(CharactersFragment::class.java, FavoritesFragment::class.java))
        }

        override fun navigateToFavorites() {
            navigation.updateUi(Screen.ShowAndHide(FavoritesFragment::class.java, CharactersFragment::class.java))
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
