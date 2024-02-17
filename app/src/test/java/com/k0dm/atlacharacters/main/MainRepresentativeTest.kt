package com.k0dm.atlacharacters.main

import com.k0dm.atlacharacters.characters.presentation.CharactersScreen
import com.k0dm.atlacharacters.core.UiObserver
import com.k0dm.atlacharacters.favorites.presentation.FavoritesScreen
import org.junit.Assert.assertEquals
import org.junit.Test

class MainRepresentativeTest {

    @Test
    fun checkNavigationAndComeBack() {
        val navigation = FakeNavigation()
        val representative = MainRepresentative.Base(navigation = navigation)

        //onCreate
        representative.init(isFirstRun = true)
        assertEquals(CharactersScreen, navigation.actualUiState)

        //onResume showing CharactersFragment
        val navigationObserver = object : UiObserver<Screen> {
            override fun updateUi(uiState: Screen) = Unit
        }
        representative.startGettingUpdates(observer = navigationObserver)
        assertEquals(navigationObserver, navigation.actualObserver)
        representative.notifyObserved()
        assertEquals(Screen.Empty, navigation.actualUiState)

        //user click at navigation bar "Favorites"
        representative.navigate(FavoritesScreen)
        assertEquals(FavoritesScreen, navigation.actualUiState)
        representative.notifyObserved()
        assertEquals(Screen.Empty, navigation.actualUiState)

        //user click at navigation bar "Characters"
        representative.navigate(FavoritesScreen)
        assertEquals(FavoritesScreen, navigation.actualUiState)
        representative.notifyObserved()
        assertEquals(Screen.Empty, navigation.actualUiState)
    }
}

private class FakeNavigation : Navigation.Mutable {

    var actualUiState: Screen = Screen.Empty
    var actualObserver: UiObserver<Screen> = NavigationObserver.Empty

    override fun updateUi(uiState: Screen) {
        actualUiState = uiState
    }

    override fun updateUiObserver(observer: UiObserver<Screen>) {
        actualObserver = observer
    }

    override fun clear() {
        actualUiState = Screen.Empty
    }
}