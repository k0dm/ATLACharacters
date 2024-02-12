package com.k0dm.atlacharacters.main

import com.k0dm.atlacharacters.core.UiObserver

interface NavigationObserver: UiObserver<Screen> {

    object Empty: NavigationObserver {
        override fun updateUi(uiState: Screen) = Unit
    }
}