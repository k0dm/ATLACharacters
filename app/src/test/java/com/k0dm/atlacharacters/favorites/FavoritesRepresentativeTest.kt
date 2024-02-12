package com.k0dm.atlacharacters.favorites

import com.k0dm.atlacharacters.characters.data.CharacterModel
import com.k0dm.atlacharacters.core.FakeRunAsync
import com.k0dm.atlacharacters.core.UiObserver
import com.k0dm.atlacharacters.favorites.domain.FavoritesDomain
import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor
import com.k0dm.atlacharacters.favorites.presentation.FavoriteCharacterUi
import com.k0dm.atlacharacters.favorites.presentation.FavoritesUiObserver
import com.k0dm.atlacharacters.favorites.presentation.FavoritesUiState
import com.k0dm.atlacharacters.favorites.presentation.FavoritesUiStateObservable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavoritesRepresentativeTest {

    private lateinit var representative: FavoritesRepresentative
    private lateinit var observable: FakeObservable
    private lateinit var interactor: FakeInteractor
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setUp() {
        observable = FakeObservable()
        interactor = FakeInteractor()
        runAsync = FakeRunAsync()
        representative = FavoritesRepresentative(
            observable = observable,
            interactor = interactor,
            runAsync = runAsync
        )
    }

    @Test
    fun expandAndCollapseItemThenRemoveFromFavorites() {

        representative.init()
        assertEquals(FavoritesUiState.Empty, observable.actualUiState)
        assertEquals(1, runAsync.startCalledCount)

        runAsync.pingResult()
        assertEquals(
            FavoritesUiState.Base(
                listOf(
                    FavoriteCharacterUi(
                        id = "0",
                        name = "Asami Sato",
                        allies = "Hiroshi Sato, Korra",
                        enemies = "Amon",
                        affiliation = "Future Industries Sato family Team Avatar",
                        photoUrl = "url0",
                        isExpanded = false
                    ),
                    FavoriteCharacterUi(
                        id = "1",
                        name = "Unalaq",
                        allies = "Northern Water Tribe",
                        enemies = "Northern Water Tribe",
                        affiliation = "Northern Water Tribe Southern Water Tribe Red Lotus (formerly) Vaatu",
                        photoUrl = "url1",
                        isExpanded = false
                    )
                )
            ),
            observable.actualUiState
        )

        //user clicks at first item
        representative.clickItem(
            FavoriteCharacterUi = FavoriteCharacterUi(
                id = "0",
                name = "Asami Sato",
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isExpanded = false
            )
        )
        assertEquals(
            FavoriteCharacterUi(
                id = "0",
                name = "Asami Sato",
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isExpanded = true
            ),
            observable.actualFavoriteCharacterUi
        )

        //user clicks at collapse button
        representative.clickItem(
            FavoriteCharacterUi = FavoriteCharacterUi(
                id = "0",
                name = "Asami Sato",
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isExpanded = true
            )
        )
        assertEquals(
            FavoriteCharacterUi(
                id = "0",
                name = "Asami Sato",
                allies = "Hiroshi Sato, Korra",
                enemies = "Amon",
                affiliation = "Future Industries Sato family Team Avatar",
                photoUrl = "url0",
                isExpanded = false
            ),
            observable.actualFavoriteCharacterUi
        )

        //user clicks at second item favoritesIcon
        representative.changeFavoriteStatus(
            FavoriteCharacterUi = FavoriteCharacterUi(
                id = "1",
                name = "Unalaq",
                allies = "Northern Water Tribe",
                enemies = "Northern Water Tribe",
                affiliation = "Northern Water Tribe Southern Water Tribe Red Lotus (formerly) Vaatu",
                photoUrl = "url1",
                isExpanded = false
            )
        )
        assertEquals(1, interactor.removeFromFavoritesCalledCount)

        runAsync.pingResult()
        assertEquals(
            FavoritesUiState.Base(
                listOf(
                    FavoriteCharacterUi(
                        id = "0",
                        name = "Asami Sato",
                        allies = "Hiroshi Sato, Korra",
                        enemies = "Amon",
                        affiliation = "Future Industries Sato family Team Avatar",
                        photoUrl = "url0",
                        isExpanded = false
                    )
                )
            ),
            observable.actualUiState
        )
    }
}

private class FakeInteractor : FavoritesInteractor {

    override suspend fun allFavorites(): FavoritesDomain {
        return FavoritesDomain.Base(
            listOf(
                CharacterModel(
                    id = "0",
                    name = "Asami Sato",
                    allies = "Hiroshi Sato, Korra",
                    enemies = "Amon",
                    affiliation = "Future Industries Sato family Team Avatar",
                    photoUrl = "url0",
                ),
                CharacterModel(
                    id = "1",
                    name = "Unalaq",
                    allies = "Northern Water Tribe",
                    enemies = "Northern Water Tribe",
                    affiliation = "Northern Water Tribe Southern Water Tribe Red Lotus (formerly) Vaatu",
                    photoUrl = "url1"
                )
            )
        )
    }

    var removeFromFavoritesCalledCount = 0

    override suspend fun removeFromFavorites(id: String) {
        removeFromFavoritesCalledCount++
    }
}

private class FakeObservable : FavoritesUiStateObservable {

    var actualUiState: FavoritesUiState = FavoritesUiState.Empty
    var actualUiObserver: UiObserver<FavoritesUiState> = FavoritesUiObserver.Empty

    override fun updateUi(uiState: FavoritesUiState) {
        actualUiState = uiState
    }

    override fun updateUiObserver(observer: UiObserver<FavoritesUiState>) {
        actualUiObserver = observer
    }

    var actualFavoriteCharacterUi: FavoriteCharacterUi = FavoriteCharacterUi.Empty

    override fun update(favoriteCharacterUi: FavoriteCharacterUi) {
        actualFavoriteCharacterUi = favoriteCharacterUi
    }

    override fun clear() = Unit
}

