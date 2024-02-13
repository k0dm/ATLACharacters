package com.k0dm.atlacharacters.favorites.presentation

interface FavoritesUiState {

    fun changeItem(favoritesCharacterUi: FavoriteCharacterUi): FavoritesUiState

    class Base(private val favoritesCharacters: List<FavoriteCharacterUi>) : FavoritesUiState {

        override fun changeItem(favoritesCharacterUi: FavoriteCharacterUi): FavoritesUiState {
            val newFavoriteCharactersList = ArrayList(favoritesCharacters)
            newFavoriteCharactersList.find { it.equals(favoritesCharacterUi) }!!.changeExpandable()
            return FavoritesUiState.Base(newFavoriteCharactersList)
        }
    }

    object Empty : FavoritesUiState {
        override fun changeItem(favoritesCharacterUi: FavoriteCharacterUi) = this
    }
}