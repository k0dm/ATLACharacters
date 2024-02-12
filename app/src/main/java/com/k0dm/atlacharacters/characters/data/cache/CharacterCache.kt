package com.k0dm.atlacharacters.characters.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterCache(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("allies")
    val allies: String,
    @ColumnInfo("enemies")
    val enemies: String,
    @ColumnInfo("affiliation")
    val affiliation: String,
    @ColumnInfo("photo_url")
    val photoUrl: String
)