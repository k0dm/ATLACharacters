package com.k0dm.atlacharacters.characters.data.cache

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterCache::class, TempCharacter::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}