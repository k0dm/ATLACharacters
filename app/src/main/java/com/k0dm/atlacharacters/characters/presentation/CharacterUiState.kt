package com.k0dm.atlacharacters.characters.presentation

import android.view.View
import com.k0dm.atlacharacters.R
import com.k0dm.atlacharacters.databinding.FragmentCharactersBinding

interface CharacterUiState {

    fun show(binding: FragmentCharactersBinding)

    data class Base(
        private val id: String,
        private val name: String,
        private val allies: String,
        private val enemies: String,
        private val affiliation: String,
        private val photoUrl: String,
        private var isFavorite: Boolean
    ) : CharacterUiState {

        override fun show(binding: FragmentCharactersBinding) = with(binding) {
            loadingLayout.visibility = View.GONE
            favoriteImageView.visibility = View.VISIBLE
            favoriteImageView.setImageResource(if (isFavorite) R.drawable.favorite else R.drawable.not_favorite)
            contentLayout.visibility = View.VISIBLE
            actionButton.visibility = View.VISIBLE
            actionButton.text = "Next"
            nameTextView.text = name
            alliesTextView.text = allies
            enemiesTextView.text = enemies
            affiliationTextView.text = affiliation
        }
    }

    object Loading : CharacterUiState {

        override fun show(binding: FragmentCharactersBinding) = with(binding) {
            loadingLayout.visibility = View.VISIBLE
            loadingProgressBar.visibility = View.VISIBLE
            loadingDataTextView.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE
            favoriteImageView.visibility = View.GONE
            contentLayout.visibility = View.GONE
            actionButton.visibility = View.GONE
        }
    }

    data class Error(private val message: String) : CharacterUiState {

        override fun show(binding: FragmentCharactersBinding) = with(binding) {
            loadingProgressBar.visibility = View.GONE
            loadingDataTextView.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
            actionButton.visibility = View.VISIBLE
            actionButton.text = "Retry"
        }
    }

    object Empty : CharacterUiState {
        override fun show(binding: FragmentCharactersBinding) = Unit
    }
}