package com.k0dm.atlacharacters.characters.presentation

import com.k0dm.atlacharacters.core.UiObservable

interface CharactersUiStateObservable: UiObservable<CharacterUiState>  {

    fun save(uiStateStore: UiStateStore)

    class Base: CharactersUiStateObservable, UiObservable.Base<CharacterUiState>(CharacterUiState.Empty) {

        override fun save(uiStateStore: UiStateStore) {
            uiStateStore.save(cachedUiState)
        }
    }
}