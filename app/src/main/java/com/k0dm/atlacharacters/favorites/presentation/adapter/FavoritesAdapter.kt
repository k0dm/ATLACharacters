package com.k0dm.atlacharacters.favorites.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.k0dm.atlacharacters.databinding.BaseCharacterItemBinding
import com.k0dm.atlacharacters.databinding.ExpandedCharacterItemBinding
import com.k0dm.atlacharacters.favorites.presentation.ClickItemAction
import com.k0dm.atlacharacters.favorites.presentation.FavoriteCharacterUi

class FavoritesAdapter(
    private val clickListener: ClickItemAction,
    private val types: List<TypeUi> = listOf(TypeUi.Base, TypeUi.Expanded)
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private val favoriteCharacters = arrayListOf<FavoriteCharacterUi>()

    fun update(newFavoritesList: List<FavoriteCharacterUi>) {
        val diffUtilCallback = FavoritesCharacterDiffUtilCallback(favoriteCharacters, newFavoritesList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        favoriteCharacters.clear()
        favoriteCharacters.addAll(newFavoritesList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return types.indexOf(favoriteCharacters[position].type())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return types[viewType].viewHolder(parent, clickListener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(favoriteCharacters[position])
    }

    override fun getItemCount() = favoriteCharacters.size
}

abstract class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(favoriteCharacterUi: FavoriteCharacterUi)

    class Base(
        private val binding: BaseCharacterItemBinding,
        private val clickListener: ClickItemAction
    ) : CharacterViewHolder(binding.root) {

        override fun bind(favoriteCharacterUi: FavoriteCharacterUi) = with(binding) {
            favoriteCharacterUi.show(binding)
            favoriteImageView.setOnClickListener {
                clickListener.changeFavoriteStatus(favoriteCharacterUi)
            }
            favoriteCharacterCollapsedLayout.setOnClickListener {
                clickListener.clickItem(favoriteCharacterUi)
            }
        }
    }

    class Expanded(
        private val binding: ExpandedCharacterItemBinding,
        private val clickListener: ClickItemAction
    ) : CharacterViewHolder(binding.root) {

        override fun bind(favoriteCharacterUi: FavoriteCharacterUi) = with(binding) {
            favoriteCharacterUi.show(binding)
            favoriteImageView.setOnClickListener {
                clickListener.changeFavoriteStatus(favoriteCharacterUi)
            }
            collapseImageView.setOnClickListener {
                clickListener.clickItem(favoriteCharacterUi)
            }
        }
    }
}

