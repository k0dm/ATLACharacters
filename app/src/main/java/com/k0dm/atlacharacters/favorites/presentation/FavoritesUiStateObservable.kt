package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.core.UiObservable

interface FavoritesUiStateObservable : UiObservable<FavoritesUiState> {

    fun update(favoriteCharacterUi: FavoriteCharacterUi)

    class Base : FavoritesUiStateObservable, UiObservable.Base<FavoritesUiState>(FavoritesUiState.Empty) {

        override fun update(favoriteCharacterUi: FavoriteCharacterUi) {
            updateUi(cachedUiState.changeItem(favoriteCharacterUi))
        }
    }
}