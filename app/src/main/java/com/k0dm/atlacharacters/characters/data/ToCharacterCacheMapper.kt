package com.k0dm.atlacharacters.characters.data

import com.k0dm.atlacharacters.characters.data.cache.CharacterCache

object ToCharacterCacheMapper : DataMapper<CharacterCache> {

    override fun map(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String
    ) = CharacterCache(id, name, allies, enemies, affiliation, photoUrl)
}