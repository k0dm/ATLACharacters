package com.k0dm.atlacharacters.characters.data.cache

import android.content.Context
import androidx.room.Room

interface CharactersCacheDataSource {

    suspend fun save(character: CharacterCache)

    suspend fun characters(): List<CharacterCache>

    suspend fun delete(id: String)

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

        override suspend fun save(character: CharacterCache) {
            dao.save(character)
        }

        override suspend fun characters() = dao.characters()

        override suspend fun delete(id: String) {
            dao.delete(id)
        }
    }
}

