package com.k0dm.atlacharacters.favorites.presentation

import com.k0dm.atlacharacters.core.Representative
import com.k0dm.atlacharacters.core.RunAsync
import com.k0dm.atlacharacters.core.UiObserver
import com.k0dm.atlacharacters.favorites.domain.FavoritesDomainMapper
import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor

interface FavoritesRepresentative : Representative<FavoritesUiState>, ClickItemAction {

    fun init(isFirstRun: Boolean)

    class Base(
        private val observable: FavoritesUiStateObservable,
        private val interactor: FavoritesInteractor,
        runAsync: RunAsync,
        private val uiMapper: FavoritesDomainMapper<FavoritesUiState> = ToFavoritesUiMapper
    ) : FavoritesRepresentative, Representative.BaseRepresentative<FavoritesUiState>(runAsync) {

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) {
                runAsync({
                    interactor.allFavorites()
                }) { favoritesDomain ->
                    observable.updateUi(favoritesDomain.map(uiMapper))
                }
            }
        }

        override fun clickItem(favoriteCharacterUi: FavoriteCharacterUi) {
            observable.update(favoriteCharacterUi)
        }

        override fun changeFavoriteStatus(favoriteCharacterUi: FavoriteCharacterUi) {
            runAsync({
                favoriteCharacterUi.removeFromFavorites(interactor)
                interactor.allFavorites()
            }) {favoritesDomain->
                observable.updateUi(favoritesDomain.map(uiMapper))
            }
        }

        override fun startGettingUpdates(observer: UiObserver<FavoritesUiState>) {
            observable.updateUiObserver(observer)
        }

        override fun stopGettingUpdates() {
            observable.updateUiObserver(FavoritesUiObserver.Empty)
        }
    }
}

interface ClickItemAction {

    fun clickItem(favoriteCharacterUi: FavoriteCharacterUi)

    fun changeFavoriteStatus(favoriteCharacterUi: FavoriteCharacterUi)
}