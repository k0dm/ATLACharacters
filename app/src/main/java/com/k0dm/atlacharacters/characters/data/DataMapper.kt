package com.k0dm.atlacharacters.characters.data

interface DataMapper<T> {
    fun map(
        id: String,
        name: String,
        allies: List<String>,
        enemies: List<String>,
        affiliation: String,
        photoUrl: String
    ): T
}

object CharacterModelMapper : DataMapper<CharacterModel> {
    override fun map(
        id: String,
        name: String,
        allies: List<String>,
        enemies: List<String>,
        affiliation: String,
        photoUrl: String
    ) = CharacterModel(id, name, allies, enemies, affiliation, photoUrl)
}