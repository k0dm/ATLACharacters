package com.k0dm.atlacharacters.characters.domain

import com.k0dm.atlacharacters.characters.data.DataMapper

class ToCharacterDomainMapper(private val isFavorite: Boolean) : DataMapper<CharacterDomain> {

    override fun map(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String
    ) = CharacterDomain.Success(id, name, allies, enemies, affiliation, photoUrl, isFavorite)
}