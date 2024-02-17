package com.k0dm.atlacharacters.characters.data

import com.k0dm.atlacharacters.characters.data.cache.CharacterCache
import com.k0dm.atlacharacters.characters.data.cache.CharactersCacheDataSource
import com.k0dm.atlacharacters.characters.data.cache.TempCharacter
import com.k0dm.atlacharacters.characters.data.cloud.CharactersCloudDataSource
import com.k0dm.atlacharacters.characters.data.cloud.CloudMapper

interface CharactersRepository {

    suspend fun randomCharacter(): CharacterModel

    suspend fun allFavoritesCharacters(): List<CharacterModel>

    suspend fun saveToCache(character: CharacterModel)

    suspend fun removeFromCache(character: CharacterModel)

   suspend fun tempCharacter(): CharacterModel

    class Base(
        private val cloudDataSource: CharactersCloudDataSource,
        private val cacheDataSource: CharactersCacheDataSource,
        private val cloudMapper: CloudMapper<CharacterModel> = ToCharacterModelMapper,
        private val dataMapper: DataMapper<CharacterCache> = ToCharacterCacheMapper,
        private val toTempMapper:DataMapper<TempCharacter> = ToTempCharacterMapper
    ) : CharactersRepository {

        override suspend fun randomCharacter(): CharacterModel {
            val characterModel = cloudDataSource.fetchRandomCharacter().execute().body()!![0]
                .map(cloudMapper)
            cacheDataSource.saveTemp(characterModel.map(toTempMapper))
            return characterModel
        }

        override suspend fun allFavoritesCharacters(): List<CharacterModel> {
            return cacheDataSource.characters().map {
                CharacterModel(
                    id = it.id,
                    name = it.name,
                    allies = it.allies,
                    enemies = it.enemies,
                    affiliation = it.affiliation,
                    photoUrl = it.photoUrl
                )
            }
        }

        override suspend fun saveToCache(character: CharacterModel) {
            cacheDataSource.saveFavorite(character.map(dataMapper))
        }

        override suspend fun removeFromCache(character: CharacterModel) {
            character.removeFromCache(cacheDataSource = cacheDataSource)
        }

        override suspend fun tempCharacter() = cacheDataSource.temp().let {
            CharacterModel(it.id, it.name, it.allies, it.enemies, it.affiliation, it.photoUrl)
        }
    }
}