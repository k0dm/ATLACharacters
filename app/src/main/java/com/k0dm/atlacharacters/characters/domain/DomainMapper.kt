package com.k0dm.atlacharacters.characters.domain

interface DomainMapper<T : Any> {

    fun mapSuccess(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String,
        isFavorite: Boolean
    ): T

    fun mapError(message: String): T
}