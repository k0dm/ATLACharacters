package com.k0dm.atlacharacters.characters.data.cloud

import com.google.gson.annotations.SerializedName

data class CharacterCloud(
    @SerializedName("_id")
    private val id: String,
    @SerializedName("name")
    private val name: String,
    @SerializedName("allies")
    private val allies: List<String>,
    @SerializedName("enemies")
    private val enemies: List<String>,
    @SerializedName("affiliation")
    private val affiliation: String?,
    @SerializedName("photoUrl")
    private val photoUrl: String
) {
    fun <T : Any> map(mapper: CloudMapper<T>): T =
        mapper.map(id, name, allies, enemies, affiliation ?: "", photoUrl)
}