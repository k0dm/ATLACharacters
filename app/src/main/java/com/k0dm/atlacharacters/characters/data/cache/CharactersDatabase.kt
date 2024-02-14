package com.k0dm.atlacharacters.characters.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterCache::class], version = 1)
abstract class CharactersDatabase: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}