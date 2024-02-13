package com.k0dm.atlacharacters.favorites.domain

import com.k0dm.atlacharacters.characters.data.CharacterModel

interface FavoritesDomainMapper<T> {

    fun map(favorites: List<CharacterModel>): T
}