package com.k0dm.atlacharacters.characters.data.cache

import com.k0dm.atlacharacters.characters.data.CharacterModel

interface CharactersCacheDataSource {

    suspend fun characters(): List<CharacterModel>

    suspend fun save(characterModel: CharacterModel)

    suspend fun delete(id: String)

    class Base(private val dao: CharactersDao): CharactersCacheDataSource {

        override suspend fun characters(): List<CharacterModel> {
            TODO("Not yet implemented")
        }

        override suspend fun save(characterModel: CharacterModel) = dao.save(characterModel.map)

        override suspend fun delete(id: String) = dao.delete(id)
    }
}