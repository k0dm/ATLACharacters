package com.k0dm.atlacharacters.characters.data

data class CharacterModel(
    private val id: String,
    private val name: String,
    private val allies: String,
    private val enemies: String,
    private val affiliation: String,
    private val photoUrl: String
) {
    fun <T : Any> map(mapper: DataMapper<T>): T =
        mapper.map(id, name, allies, enemies, affiliation, photoUrl)
}
