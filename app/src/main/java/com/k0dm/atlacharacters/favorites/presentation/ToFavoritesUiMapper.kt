package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.characters.data.CharacterModel
import com.k0dm.atlacharacters.favorites.domain.FavoritesDomainMapper

object ToFavoritesUiMapper : FavoritesDomainMapper<FavoritesUiState> {

    override fun map(favorites: List<CharacterModel>): FavoritesUiState {

        return FavoritesUiState.Base(favorites.map { it.map(ToFavoriteCharacterUiMapper(false)) })
    }
}