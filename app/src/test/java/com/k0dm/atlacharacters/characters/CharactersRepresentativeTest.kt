package com.k0dm.atlacharacters.characters

import com.k0dm.atlacharacters.core.FakeRunAsync
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharactersRepresentativeTest {

    private lateinit var representative: CharactersRepresentative
    private lateinit var observable: FakeObservable
    private lateinit var interactor: FakeInteractor
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setUp() {
        observable = FakeObservable()
        interactor = FakeInteractor()

        representative = CharactersRepresentative(
            observable = observable,
            interactor = interactor,
            runAsync = runAsync
        )
    }

    @Test
    fun firstErrorThanSuccessAndAddToFavorite() {
        interactor.successResponse = false

        representative.init()
        assertEquals(CharacterUiState.Loading, observable.actualUiState)
        assertEquals(1, runAsync.startCalledCount)

        runAsync.pingResult()
        assertEquals(
            CharacterUiState.Error(message = "Service unavailable"),
            observable.actualUiState
        )

        interactor.successResponse = true

        //user clicks retry button
        representative.randomCharacter()
        assertEquals(CharacterUiState.Loading, observable.actualUiState)
        assertEquals(2, runAsync.startCalledCount)

        runAsync.pingResult()
        assertEquals(
            CharacterUiState.Base(
                id = "0",
                name = "Asami Sato",
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isFavorite = false
            )
        )

        //user adds to favorites
        representative.addToFavorite()
        assertEquals(1, interactor.changeFavoriteStatusCalledCount)
        assertEquals(3, runAsync.startCalledCount)

        runAsync.pingResult()
        assertEquals(
            CharacterUiState.Base(
                id = "0",
                name = "Asami Sato",
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isFavorite = true
            )
        )
    }
}

private class FakeInteractor : CharactersInteractor {

    var successResponse: Boolean = false

    override suspend fun randomCharacter(): CharacterDomain {
        return if (successResponse) {
            CharacterDomain.Success(
                id = "0",
                name = "Asami Sato",
                allies = listOf<String>("Hiroshi Sato", "Korra"),
                enemies = listOf<String>("Amon"),
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isFavorite = false
            )
        } else {
            CharacterDomain.Error(message = "Service unavailable")
        }
    }

    var changeFavoriteStatusCalledCount = 0

    override suspend fun changeFavoriteStatus() {
        changeFavoriteStatusCalledCount++
    }
}

private class FakeObservable : CharactersUiStateObservable {

    var actualUiState: CharactersUiState = CharactersUiState.Empty
    var actualUiObserver: UiObserver<CharactersUiState> = CharactersUiObserver.Empty

    override fun updateUi(uiState: CharactersUiState) {
        actualUiState = uiState
    }

    override fun updateUiObsever(observer: UiObserver<CharactersUiState>) {
        actualUiObserver = observer
    }
}

