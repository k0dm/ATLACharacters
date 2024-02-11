package com.k0dm.atlacharacters.main

import androidx.lifecycle.Observer
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class MainRepresentativeTest {

    @Test
    fun checkNavigationAndComeBack() {
        val navigation = FakeNavigation()
        val representative = MainRepresentative(navigation = navigation)

        //onCreate
        representative.init(isFirstRun = true)
        assertEquals(CharactersScreen, navigation.actualUiState)

        //onResume showing CharactersFragment
        var navigationObserver = object : UiObserver<Screen>{
            override fun updateUiObserver(observer: Observer<Screen>) = Unit
        }
        representative.startGettingUpdates(observer = navigationObserver)
        Assert.assertEquals(navigationObserver, navigation.actualObserver)
        representative.notifyObserved()
        assertEquals(Screen.Empty, navigation.actualUiState)

        //user click at navigation bar "Favorites"
        representative.navigate(FavoritesScreen)
        assertEquals(FavoritesScreen, navigation.actualUiState)
        representative.notifyObserved()
        assertEquals(Screen.Empty, navigation.actualUiState)

        //user click at navigation bar "Characters"
        representative.navigate(FavoritesScreen)
        assertEquals(CharactersScreen, navigation.actualUiState)
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
}