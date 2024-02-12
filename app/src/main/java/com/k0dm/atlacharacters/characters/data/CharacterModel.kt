package com.k0dm.atlacharacters.characters.data

data class CharacterModel(
    private val id: String,
    private val name: String,
    private val allies: List<String>,
    private val enemies: List<String>,
    private val affiliation: String,
    private val photoUrl: String
) {
    fun <T : Any> map(mapper: DataMapper<T>): T =
        mapper.map(id, name, allies, enemies, affiliation, photoUrl)
}
