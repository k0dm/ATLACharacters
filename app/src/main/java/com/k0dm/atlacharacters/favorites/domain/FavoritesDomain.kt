package com.k0dm.atlacharacters.favorites.domain

import com.k0dm.atlacharacters.characters.data.CharacterModel

interface FavoritesDomain {

    data class Base(private val favorites: List<CharacterModel>) : FavoritesDomain
}