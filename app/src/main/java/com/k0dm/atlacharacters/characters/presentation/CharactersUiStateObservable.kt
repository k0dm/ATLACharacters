package com.k0dm.atlacharacters.characters.presentation

import com.k0dm.atlacharacters.core.UiObservable

interface CharactersUiStateObservable: UiObservable<CharacterUiState>  {

    class Base: CharactersUiStateObservable, UiObservable.Base<CharacterUiState>(CharacterUiState.Empty)
}