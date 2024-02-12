package com.k0dm.atlacharacters.characters.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(character: CharacterCache)

    @Query("SELECT * FROM characters_table")
    suspend fun characters(): List<CharacterCache>

    @Query("DELETE FROM characters_table WHERE id = :id")
    suspend fun delete(id: String)
}