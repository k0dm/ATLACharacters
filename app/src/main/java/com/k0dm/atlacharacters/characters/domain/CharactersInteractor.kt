package com.k0dm.atlacharacters.characters.domain

import com.k0dm.atlacharacters.characters.data.CharacterModel
import com.k0dm.atlacharacters.characters.data.CharactersRepository
import com.k0dm.atlacharacters.characters.data.DataMapper
import java.net.UnknownHostException

interface CharactersInteractor {

    suspend fun randomCharacter(): CharacterDomain

    suspend fun changeFavoriteStatus(): CharacterDomain

    class Base(
        private val repository: CharactersRepository,
        private val toSuccessFavoriteMapper: DataMapper<CharacterDomain>,
        private val toSuccessNotFavoriteMapper: DataMapper<CharacterDomain>,
    ) : CharactersInteractor {

        private var cacheCharacterModel: CharacterModel? = null
        private var cacheFavoriteCharacters: List<CharacterModel>? = null

        override suspend fun randomCharacter(): CharacterDomain {
            return try {
                cacheCharacterModel = repository.randomCharacter()
                cacheFavoriteCharacters = repository.allFavoritesCharacters()
                val mapper = if (cacheFavoriteCharacters!!.contains(cacheCharacterModel)) {
                    toSuccessFavoriteMapper
                } else {
                    toSuccessNotFavoriteMapper
                }
                cacheCharacterModel!!.map(mapper)
            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    CharacterDomain.Error(message = "No internet connection")
                } else {
                    CharacterDomain.Error(message = "Service unavailable")
                }
            }
        }

        override suspend fun changeFavoriteStatus(): CharacterDomain {
            cacheFavoriteCharacters = repository.allFavoritesCharacters()
            return if (cacheFavoriteCharacters!!.contains(cacheCharacterModel)) {
                repository.removeFromCache(cacheCharacterModel!!)
                cacheCharacterModel!!.map(toSuccessNotFavoriteMapper)
            } else {
                repository.saveToCache(cacheCharacterModel!!)
                cacheCharacterModel!!.map(toSuccessFavoriteMapper)
            }
        }
    }
}

