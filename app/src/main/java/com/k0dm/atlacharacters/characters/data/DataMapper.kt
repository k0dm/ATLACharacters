package com.k0dm.atlacharacters.characters.data

interface DataMapper<T> {
    fun map(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String
    ): T
}