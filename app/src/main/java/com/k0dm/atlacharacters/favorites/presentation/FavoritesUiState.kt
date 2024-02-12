package com.k0dm.atlacharacters.favorites.presentation

interface FavoritesUiState{

    class Base(private val favoritesCharacters: List<FavoriteCharacterUi>): FavoritesUiState

    object Empty: FavoritesUiState
}