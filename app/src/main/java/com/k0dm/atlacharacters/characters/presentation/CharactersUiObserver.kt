package com.k0dm.atlacharacters.characters.presentation

import com.k0dm.atlacharacters.core.UiObserver

interface CharactersUiObserver : UiObserver<CharacterUiState> {

    object Empty : CharactersUiObserver {
        override fun updateUi(uiState: CharacterUiState) = Unit
    }
}

