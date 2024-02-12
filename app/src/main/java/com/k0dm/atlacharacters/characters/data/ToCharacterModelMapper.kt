package com.k0dm.atlacharacters.characters.data

import com.k0dm.atlacharacters.characters.data.cloud.CloudMapper

object ToCharacterModelMapper : CloudMapper<CharacterModel> {

    override fun map(
        id: String,
        name: String,
        allies: List<String>,
        enemies: List<String>,
        affiliation: String,
        photoUrl: String
    ): CharacterModel {
        val alliesString = if (allies.isNotEmpty()) {
            val listString = allies.toString()
            listString.substring(1, listString.lastIndex)
        } else ""
        val enemiesString = if (enemies.isNotEmpty()) {
            val listString = enemies.toString()
            listString.substring(1, listString.lastIndex)
        } else ""
        return CharacterModel(id, name, alliesString, enemiesString, affiliation, photoUrl)
    }
}