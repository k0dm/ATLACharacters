package com.k0dm.atlacharacters.favorites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.k0dm.atlacharacters.databinding.BaseCharacterItemBinding
import com.k0dm.atlacharacters.databinding.ExpandedCharacterItemBinding
import com.k0dm.atlacharacters.favorites.presentation.ClickItemAction

interface TypeUi {

    fun viewHolder(parent: ViewGroup, clickListener: ClickItemAction): CharacterViewHolder

    object Base : TypeUi {

        override fun viewHolder(parent: ViewGroup, clickListener: ClickItemAction) =
            CharacterViewHolder.Base(
                BaseCharacterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                clickListener
            )
    }

    object Expanded : TypeUi {

        override fun viewHolder(parent: ViewGroup, clickListener: ClickItemAction) =
            CharacterViewHolder.Expanded(
                ExpandedCharacterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                clickListener
            )
    }
}