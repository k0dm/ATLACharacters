package com.k0dm.atlacharacters.characters.domain

interface CharacterDomain {


    data class Success(
        private val id:String,
        private val name: String,
        private val allies : String,
        private val enemies: String,
        private val affiliation: String,
        private val photoUrl: String,
        private val isFavorite: Boolean
    ): CharacterDomain


    data class Error(private val message: String): CharacterDomain
}