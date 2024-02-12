package com.k0dm.atlacharacters.characters.data

import com.k0dm.atlacharacters.characters.data.cloud.CharactersCloudDataSource
import com.k0dm.atlacharacters.characters.data.cloud.CloudMapper

interface CharactersRepository {

    suspend fun randomCharacter(): CharacterModel


    class Base(
        private val cloudDataSource: CharactersCloudDataSource,
        private val cloudMapper: CloudMapper<CharacterModel>
    ): CharactersRepository {

        override suspend fun randomCharacter(): CharacterModel {
            val characterCloud = cloudDataSource.fetchRandomCharacter().execute().body()!!
            return characterCloud.map(cloudMapper)
        }
    }
}