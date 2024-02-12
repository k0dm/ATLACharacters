package com.k0dm.atlacharacters.characters.data

import com.k0dm.atlacharacters.characters.data.cache.CharacterCache
import com.k0dm.atlacharacters.characters.data.cache.CharactersCacheDataSource
import com.k0dm.atlacharacters.characters.data.cloud.CharactersCloudDataSource
import com.k0dm.atlacharacters.characters.data.cloud.CloudMapper

interface CharactersRepository {

    suspend fun randomCharacter(): CharacterModel

    suspend fun allFavoritesCharacters(): List<CharacterModel>

    suspend fun saveToCache(character: CharacterModel)

    suspend fun removeFromCache(character: CharacterModel)

    class Base(
        private val cloudDataSource: CharactersCloudDataSource,
        private val cacheDataSource: CharactersCacheDataSource,
        private val cloudMapper: CloudMapper<CharacterModel>,
        private val dataMapper: DataMapper<CharacterCache>
    ) : CharactersRepository {

        override suspend fun randomCharacter(): CharacterModel {
            val characterCloud = cloudDataSource.fetchRandomCharacter().execute().body()!!
            return characterCloud.map(cloudMapper)
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
            cacheDataSource.save(character.map(dataMapper))
        }

        override suspend fun removeFromCache(character: CharacterModel) {
            character.removeFromCache(cacheDataSource = cacheDataSource)
        }
    }
}