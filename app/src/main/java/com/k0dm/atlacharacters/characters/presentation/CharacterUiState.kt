package com.k0dm.atlacharacters.characters.presentation

import android.view.View
import androidx.core.content.ContextCompat
import com.k0dm.atlacharacters.R
import com.k0dm.atlacharacters.databinding.FragmentCharactersBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

interface CharacterUiState : Serializable{

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
            Picasso.get().load(photoUrl).resize(333, 250)
                .placeholder(R.drawable.last_airbender_and_legend_of_korra).into(characterImageView)
            nameTextView.text = name
            alliesTextView.text = allies
            enemiesTextView.text = enemies
            affiliationTextView.text = affiliation
            actionButton.visibility = View.VISIBLE
            actionButton.setTextColor(ContextCompat.getColor(actionButton.context, R.color.white))
            actionButton.setBackgroundColor(ContextCompat.getColor(actionButton.context, R.color.secondary_container))
            actionButton.setStrokeWidthResource(R.dimen.next_button_stroke_width)
            actionButton.text = "Next"
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
            actionButton.setTextColor(ContextCompat.getColor(actionButton.context, R.color.secondary_container))
            actionButton.setBackgroundColor(ContextCompat.getColor(actionButton.context, R.color.background))
            actionButton.setStrokeWidthResource(R.dimen.retry_button_stroke_width)
        }
    }

    object Empty : CharacterUiState {
        override fun show(binding: FragmentCharactersBinding) = Unit
    }
}