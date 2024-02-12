package com.k0dm.atlacharacters.favorites.presentation

data class FavoriteCharacterUi(
    private val id: String,
    private val name: String,
    private val allies: String,
    private val enemies: String,
    private val affiliation: String,
    private val photoUrl: String,
    private var isExpanded: Boolean
)