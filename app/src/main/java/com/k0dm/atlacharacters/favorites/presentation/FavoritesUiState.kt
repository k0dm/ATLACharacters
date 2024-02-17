package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.favorites.presentation.adapter.FavoritesAdapter

interface FavoritesUiState {

    fun changeItem(favoritesCharacterUi: FavoriteCharacterUi): FavoritesUiState

    fun update(adapter: FavoritesAdapter)

    data class Base(private val favoritesCharacters: List<FavoriteCharacterUi>) : FavoritesUiState {

        override fun changeItem(favoritesCharacterUi: FavoriteCharacterUi): FavoritesUiState {
            val newFavoriteCharactersList = ArrayList(favoritesCharacters)
            val characterUi = newFavoriteCharactersList.find { it.equals(favoritesCharacterUi) }
            val index = newFavoriteCharactersList.indexOf(characterUi)
            newFavoriteCharactersList[index]= characterUi!!.changeExpandable()
            return Base(newFavoriteCharactersList)
        }

        override fun update(adapter: FavoritesAdapter) {
            adapter.update(favoritesCharacters)
        }
    }

    object Empty : FavoritesUiState {
        override fun changeItem(favoritesCharacterUi: FavoriteCharacterUi) = this
        override fun update(adapter: FavoritesAdapter) = Unit
    }
}