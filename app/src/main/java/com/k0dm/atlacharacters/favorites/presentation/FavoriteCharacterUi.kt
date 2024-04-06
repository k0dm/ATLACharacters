package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.R
import com.k0dm.atlacharacters.databinding.BaseCharacterItemBinding
import com.k0dm.atlacharacters.databinding.ExpandedCharacterItemBinding
import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor
import com.k0dm.atlacharacters.favorites.presentation.adapter.TypeUi
import com.squareup.picasso.Picasso

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
        Picasso.get().load(photoUrl).placeholder(R.drawable.last_airbender_and_legend_of_korra)
            .into(binding.characterCollapsedImageView)
    }

    fun show(binding: ExpandedCharacterItemBinding) = with(binding) {
        nameTextView.text = name
        alliesTextView.text = allies
        enemiesTextView.text = enemies
        affiliationTextView.text = affiliation
        Picasso.get().load(photoUrl).placeholder(R.drawable.last_airbender_and_legend_of_korra)
            .into(characterImageView)
    }
}