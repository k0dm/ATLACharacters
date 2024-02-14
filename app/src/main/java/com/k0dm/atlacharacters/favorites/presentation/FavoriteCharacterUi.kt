package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.databinding.BaseCharacterItemBinding
import com.k0dm.atlacharacters.databinding.ExpandedCharacterItemBinding
import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor
import com.k0dm.atlacharacters.favorites.presentation.adapter.TypeUi

data class FavoriteCharacterUi(
    private val id: String,
    private val name: String,
    private val allies: String,
    private val enemies: String,
    private val affiliation: String,
    private val photoUrl: String,
    private val isExpanded: Boolean
) {

    fun type() = if (isExpanded) TypeUi.Expanded else TypeUi.Base

    fun changeExpandable() = copy(isExpanded = !isExpanded)

    suspend fun removeFromFavorites(interactor: FavoritesInteractor) {
        interactor.removeFromFavorites(id)
    }

    fun areItemsTheSame(favoriteCharacterUi: FavoriteCharacterUi) = favoriteCharacterUi.id == id

    fun show(binding: BaseCharacterItemBinding) {
        binding.characterNameTextView.text = name
    }

    fun show(binding: ExpandedCharacterItemBinding) = with(binding){
        nameTextView.text = name
        alliesTextView.text =allies
        enemiesTextView.text=enemies
        affiliationTextView.text=affiliation

    }
}