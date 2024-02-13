package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.characters.data.DataMapper

class ToFavoriteCharacterUiMapper(private val isExpanded: Boolean) :
    DataMapper<FavoriteCharacterUi> {

    override fun map(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String
    ) = FavoriteCharacterUi(id, name, allies, enemies, affiliation, photoUrl, isExpanded)
}