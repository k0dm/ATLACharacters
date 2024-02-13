package com.k0dm.atlacharacters.characters.presentation

import com.k0dm.atlacharacters.characters.domain.CharactersInteractor
import com.k0dm.atlacharacters.characters.domain.DomainMapper
import com.k0dm.atlacharacters.core.Representative
import com.k0dm.atlacharacters.core.RunAsync
import com.k0dm.atlacharacters.core.UiObserver

interface CharactersRepresentative : Representative<CharacterUiState> {

    fun init(isFirstRun: Boolean)

    fun randomCharacter()

    fun changeFavoriteStatus()

    class Base(
        private val observable: CharactersUiStateObservable,
        private val interactor: CharactersInteractor,
        runAsync: RunAsync,
        private val uiMapper: DomainMapper<CharacterUiState> = ToCharacterUiMapper
    ) : CharactersRepresentative, Representative.BaseRepresentative<CharacterUiState>(runAsync) {

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) randomCharacter()
        }

        override fun randomCharacter() {
            observable.updateUi(CharacterUiState.Loading)
            runAsync({
                interactor.randomCharacter()
            }) { characterDomain ->
                observable.updateUi(characterDomain.map(uiMapper))
            }
        }

        override fun changeFavoriteStatus() {
            runAsync({
                interactor.changeFavoriteStatus()
            }) { characterDomain ->
                observable.updateUi(characterDomain.map(uiMapper))
            }
        }

        override fun startGettingUpdates(observer: UiObserver<CharacterUiState>) {
            observable.updateUiObserver(observer)
        }

        override fun stopGettingUpdates() {
            observable.updateUiObserver(CharactersUiObserver.Empty)
        }
    }
}

