package com.k0dm.atlacharacters.characters

import com.k0dm.atlacharacters.characters.domain.CharacterDomain
import com.k0dm.atlacharacters.characters.domain.CharactersInteractor
import com.k0dm.atlacharacters.characters.presentation.CharacterUiState
import com.k0dm.atlacharacters.characters.presentation.CharactersRepresentative
import com.k0dm.atlacharacters.characters.presentation.CharactersUiObserver
import com.k0dm.atlacharacters.characters.presentation.CharactersUiStateObservable
import com.k0dm.atlacharacters.characters.presentation.UiStateStore
import com.k0dm.atlacharacters.core.FakeRunAsync
import com.k0dm.atlacharacters.core.UiObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharactersRepresentativeTest {

    private lateinit var representative: CharactersRepresentative
    private lateinit var observable: FakeObservable
    private lateinit var interactor: FakeInteractor
    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiStateStore: FakeUiStateStore

    @Before
    fun setUp() {
        observable = FakeObservable()
        interactor = FakeInteractor()
        runAsync = FakeRunAsync()
        uiStateStore = FakeUiStateStore()

        representative = CharactersRepresentative.Base(
            observable = observable,
            interactor = interactor,
            runAsync = runAsync
        )
    }

    @Test
    fun firstErrorThanSuccessAndAddToFavorite() {
        interactor.successResponse = false
        uiStateStore.isFirstRun = true

        representative.init(uiStateStore)
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
            ),
            observable.actualUiState
        )

        //user adds to favorites
        interactor.isFavorite = true
        representative.changeFavoriteStatus()
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
            ),
            observable.actualUiState
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
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isFavorite = false
            )
        } else {
            CharacterDomain.Error(message = "Service unavailable")
        }
    }

    var changeFavoriteStatusCalledCount = 0
    var isFavorite = false

    override suspend fun changeFavoriteStatus(): CharacterDomain {
        changeFavoriteStatusCalledCount++
        return CharacterDomain.Success(
            id = "0",
            name = "Asami Sato",
            allies = "Hiroshi Sato, Korra",
            enemies = "Amon",
            affiliation = "Future Industries Sato family Team Avatar",
            photoUrl = "url0",
            isFavorite = true
        )
    }

    override suspend fun actualData(): CharacterDomain {
        throw IllegalStateException("Don't use in Unit test")
    }
}

private class FakeObservable : CharactersUiStateObservable {

    var actualUiState: CharacterUiState = CharacterUiState.Empty
    var actualUiObserver: UiObserver<CharacterUiState> = CharactersUiObserver.Empty

    override fun updateUi(uiState: CharacterUiState) {
        actualUiState = uiState
    }

    override fun save(uiStateStore: UiStateStore) {
        uiStateStore.save(actualUiState)
    }

    override fun updateUiObserver(observer: UiObserver<CharacterUiState>) {
        actualUiObserver = observer
    }

    override fun clear() = Unit
}

private class FakeUiStateStore: UiStateStore {

    var savedUiState: CharacterUiState = CharacterUiState.Empty
    var isFirstRun = false

    override fun isEmpty()  = isFirstRun

    override fun save(characterUiState: CharacterUiState) {
        savedUiState = characterUiState
    }

    override fun restore() = savedUiState
}