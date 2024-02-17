package com.k0dm.atlacharacters.characters.data

import com.k0dm.atlacharacters.characters.data.cache.TempCharacter


object ToTempCharacterMapper: DataMapper<TempCharacter> {

    override fun map(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String
    ) = TempCharacter(id, name, allies, enemies, affiliation, photoUrl)
}