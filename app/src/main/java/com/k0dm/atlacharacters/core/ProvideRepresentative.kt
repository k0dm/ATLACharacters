package com.k0dm.atlacharacters.core

import com.k0dm.atlacharacters.characters.presentation.CharactersRepresentative
import com.k0dm.atlacharacters.favorites.presentation.FavoritesRepresentative
import com.k0dm.atlacharacters.main.MainRepresentative

@Suppress("UNCHECKED_CAST")
interface ProvideRepresentative {

    fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T

    class Factory(private val makeRepresentative: ProvideRepresentative) : ProvideRepresentative {

        private val representativeStore =
            mutableMapOf<Class<out Representative<*>>, Representative<*>>()

        override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T {
            return if (representativeStore.contains(clazz)) {
                representativeStore[clazz]
            } else {
                val representative = makeRepresentative.provideRepresentative(clazz)
                representativeStore[clazz] = representative
                representative
            } as T
        }
    }

    class MakeRepresentative(private val core: Core) : ProvideRepresentative {

        override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T {

            return when (clazz) {
                MainRepresentative::class.java -> MainRepresentative.Base(core.navigation())

                CharactersRepresentative::class.java -> CharactersRepresentative.Base(
                    core.charactersUiStateObservable(),
                    core.charactersInteractor(),
                    core.runAsync()
                )

                FavoritesRepresentative::class.java -> FavoritesRepresentative.Base(
                    core.favoritesUiStateObservable(),
                    core.favoritesInteractor(),
                    core.runAsync()
                )

                else -> throw IllegalStateException("No such representative with clazz:$clazz")
            } as T
        }
    }
}