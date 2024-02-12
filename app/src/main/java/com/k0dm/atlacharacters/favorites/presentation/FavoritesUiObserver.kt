package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.core.UiObserver

interface FavoritesUiObserver: UiObserver<FavoritesUiState> {

    object Empty: FavoritesUiObserver {
        override fun updateUi(uiState: FavoritesUiState) = Unit
    }
}

