package com.k0dm.atlacharacters.characters.data.cache

import android.content.Context
import androidx.room.Room

interface CharactersCacheDataSource {

    suspend fun saveFavorite(character: CharacterCache)

    suspend fun characters(): List<CharacterCache>

    suspend fun delete(id: String)

    suspend fun saveTemp(tempCharacter: TempCharacter)

    suspend fun temp(): TempCharacter

    class Base(context: Context): CharactersCacheDataSource {

        private val database by lazy {
            Room.databaseBuilder(
                context = context,
                CharactersDatabase::class.java,
                "favorite_characters_db"
            ).build()
        }

        private val dao by lazy {
            database.charactersDao()
        }

        override suspend fun saveFavorite(character: CharacterCache) {
            dao.save(character)
        }

        override suspend fun characters() = dao.characters()

        override suspend fun delete(id: String) {
            dao.delete(id)
        }

        override suspend fun saveTemp(tempCharacter: TempCharacter) {
            dao.clearTemp()
            dao.saveTemp(tempCharacter)
        }

        override suspend fun temp() = dao.temp()[0]
    }
}

