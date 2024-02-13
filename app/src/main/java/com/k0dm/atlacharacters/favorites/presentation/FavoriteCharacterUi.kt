package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor

data class FavoriteCharacterUi(
    private val id: String,
    private val name: String,
    private val allies: String,
    private val enemies: String,
    private val affiliation: String,
    private val photoUrl: String,
    private var isExpanded: Boolean
) {

    fun changeExpandable() {
        isExpanded = !isExpanded
    }

    suspend fun removeFromFavorites(interactor: FavoritesInteractor) {
        interactor.removeFromFavorites(id)
    }
}