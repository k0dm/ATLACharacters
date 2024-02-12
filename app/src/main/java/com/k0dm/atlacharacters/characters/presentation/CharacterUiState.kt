package com.k0dm.atlacharacters.characters.presentation

interface CharacterUiState {

    data class Base(
        private val id: String,
        private val name: String,
        private val allies: String,
        private val enemies: String,
        private val affiliation: String,
        private val photoUrl: String,
        private var isFavorite: Boolean
    )

    object Loading: CharacterUiState

    data class Error(private val message: String) : CharacterUiState

    object Empty: CharacterUiState
}