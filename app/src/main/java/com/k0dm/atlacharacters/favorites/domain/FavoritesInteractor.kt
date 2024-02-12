package com.k0dm.atlacharacters.favorites.domain

import com.k0dm.atlacharacters.characters.data.CharacterModel
import com.k0dm.atlacharacters.characters.data.cache.CharactersCacheDataSource

interface FavoritesInteractor {

    suspend fun allFavorites(): FavoritesDomain

    suspend fun removeFromFavorites(id: String)

    class Base(private val cacheDataSource: CharactersCacheDataSource) : FavoritesInteractor {

        override suspend fun allFavorites() = FavoritesDomain.Base(
            cacheDataSource.characters().map {
                CharacterModel(
                    id = it.id,
                    name = it.name,
                    allies = it.allies,
                    enemies = it.enemies,
                    affiliation = it.affiliation,
                    photoUrl = it.photoUrl
                )
            })

        override suspend fun removeFromFavorites(id: String) {
            cacheDataSource.delete(id)
        }
    }
}

