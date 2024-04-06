package com.k0dm.atlacharacters.characters.domain

import com.k0dm.atlacharacters.characters.data.CharactersRepository
import com.k0dm.atlacharacters.characters.data.DataMapper
import java.net.UnknownHostException

interface CharactersInteractor {

    suspend fun randomCharacter(): CharacterDomain

    suspend fun changeFavoriteStatus(): CharacterDomain

    suspend fun actualData(): CharacterDomain

    class Base(
        private val repository: CharactersRepository,
        private val toSuccessFavoriteMapper: DataMapper<CharacterDomain> = ToCharacterDomainMapper(
            true
        ),
        private val toSuccessNotFavoriteMapper: DataMapper<CharacterDomain> = ToCharacterDomainMapper(
            false
        ),
    ) : CharactersInteractor {

        override suspend fun randomCharacter(): CharacterDomain {
            return try {
                val cacheCharacterModel = repository.randomCharacter()
                val cacheFavoriteCharacters = repository.allFavoritesCharacters()
                val mapper = if (cacheFavoriteCharacters.contains(cacheCharacterModel)) {
                    toSuccessFavoriteMapper
                } else {
                    toSuccessNotFavoriteMapper
                }
                cacheCharacterModel.map(mapper)
            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    CharacterDomain.Error(message = "No internet connection")
                } else {
                    CharacterDomain.Error(message = "Service unavailable")
                }
            }
        }

        override suspend fun changeFavoriteStatus(): CharacterDomain {
            val cacheFavoriteCharacters = repository.allFavoritesCharacters()
            val cacheCharacterModel = repository.tempCharacter()
            val mapper = if (cacheFavoriteCharacters.contains(cacheCharacterModel)) {
                repository.removeFromCache(cacheCharacterModel)
                toSuccessNotFavoriteMapper
            } else {
                repository.saveToCache(cacheCharacterModel)
                toSuccessFavoriteMapper
            }
            return cacheCharacterModel.map(mapper)
        }

        override suspend fun actualData(): CharacterDomain {
            val cacheFavoriteCharacters = repository.allFavoritesCharacters()
            val cacheCharacterModel = repository.tempCharacter()
            val mapper = if (cacheFavoriteCharacters.contains(cacheCharacterModel)) {
                toSuccessFavoriteMapper
            } else {
                toSuccessNotFavoriteMapper
            }
            return cacheCharacterModel.map(mapper)
        }
    }

    class MockForUiTests : CharactersInteractor { //TODO: Write correct logic

        val charactersDomain = mutableListOf(
            CharacterDomain.MockForUiTest(
                id = "0",
                name = "Katara",
                allies = "Team Avatar",
                enemies = "Fire Nation",
                affiliation = "Team Avatar",
                photoUrl = "url1",
                isFavorite = false
            ),
            CharacterDomain.Error("Service unavailable"),
            CharacterDomain.MockForUiTest(
                id = "1",
                name = "Aang",
                allies = "Team Avatar",
                enemies = "Firelord Ozai, Azula",
                affiliation = "Team Avatar",
                photoUrl = "url2",
                isFavorite = false
            )
        )

        private var index = 0

        override suspend fun randomCharacter(): CharacterDomain {
            if (index >= charactersDomain.lastIndex) index = 0
            val characterDomain = charactersDomain[index]
            return if (characterDomain is CharacterDomain.MockForUiTest) {
                characterDomain.toSuccess()
            } else {
                characterDomain
            }
        }

        override suspend fun changeFavoriteStatus(): CharacterDomain {
            return (charactersDomain[index] as CharacterDomain.MockForUiTest).changeFavorite()

        }

        override suspend fun actualData() = charactersDomain[index]
    }
}