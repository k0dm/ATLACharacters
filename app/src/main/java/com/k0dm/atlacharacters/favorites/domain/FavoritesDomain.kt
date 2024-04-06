package com.k0dm.atlacharacters.favorites.domain

import com.k0dm.atlacharacters.characters.data.CharacterModel

interface FavoritesDomain {

    fun <T : Any> map(mapper: FavoritesDomainMapper<T>): T

    data class Base(private val favorites: List<CharacterModel>) : FavoritesDomain {

        override fun <T : Any> map(mapper: FavoritesDomainMapper<T>) = mapper.map(favorites)
    }
}

