package com.k0dm.atlacharacters.characters.data.cloud

interface CloudMapper<T> {

    fun map(
        id: String,
        name: String,
        allies: List<String>,
        enemies: List<String>,
        affiliation: String,
        photoUrl: String
    ): T
}

