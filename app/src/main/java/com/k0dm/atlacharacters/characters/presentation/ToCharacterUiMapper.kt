package com.k0dm.atlacharacters.characters.presentation

import com.k0dm.atlacharacters.characters.domain.DomainMapper

object ToCharacterUiMapper : DomainMapper<CharacterUiState> {

    override fun mapSuccess(
        id: String,
        name: String,
        allies: String,
        enemies: String,
        affiliation: String,
        photoUrl: String,
        isFavorite: Boolean
    ) = CharacterUiState.Base(id, name, allies, enemies, affiliation, photoUrl, isFavorite)

    override fun mapError(message: String) = CharacterUiState.Error(message)
}