package com.k0dm.atlacharacters.characters.data.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CharactersCloudDataSource {

    @GET("/api/v1/characters/random")
    fun fetchRandomCharacter(): Call<List<CharacterCloud>>
}

