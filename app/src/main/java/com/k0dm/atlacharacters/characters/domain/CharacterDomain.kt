package com.k0dm.atlacharacters.characters.domain

interface CharacterDomain {

    fun <T : Any> map(mapper: DomainMapper<T>): T

    data class Success(
        private val id: String,
        private val name: String,
        private val allies: String,
        private val enemies: String,
        private val affiliation: String,
        private val photoUrl: String,
        private val isFavorite: Boolean
    ) : CharacterDomain {

        override fun <T : Any> map(mapper: DomainMapper<T>)=
            mapper.mapSuccess(id, name, allies, enemies, affiliation, photoUrl, isFavorite)
    }

    data class Error(private val message: String) : CharacterDomain {

        override fun <T : Any> map(mapper: DomainMapper<T>): T  =
            mapper.mapError(message)
    }

    data class MockForUiTest(
         val id: String,
         val name: String,
         val allies: String,
         val enemies: String,
         val affiliation: String,
         val photoUrl: String,
         var isFavorite: Boolean
    ) : CharacterDomain {

        fun changeFavorite(): MockForUiTest {
            isFavorite = !isFavorite
            return this
        }

        fun toSuccess(): CharacterDomain = Success(id, name, allies, enemies, affiliation, photoUrl, isFavorite)

        override fun <T : Any> map(mapper: DomainMapper<T>)=
            mapper.mapSuccess(id, name, allies, enemies, affiliation, photoUrl, isFavorite)
    }
}

