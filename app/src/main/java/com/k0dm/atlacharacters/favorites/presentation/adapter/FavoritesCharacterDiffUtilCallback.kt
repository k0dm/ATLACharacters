package com.k0dm.atlacharacters.favorites.presentation.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.k0dm.atlacharacters.favorites.presentation.FavoriteCharacterUi

class FavoritesCharacterDiffUtilCallback(
    private val oldList: List<FavoriteCharacterUi>,
    private val newList: List<FavoriteCharacterUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)=
        oldList[oldItemPosition].areItemsTheSame(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        Log.d("k0dm", "areContentsTheSame = ${oldList[oldItemPosition]} ${newList[newItemPosition]}")
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}