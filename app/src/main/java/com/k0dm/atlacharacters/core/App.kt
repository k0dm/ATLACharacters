package com.k0dm.atlacharacters.core

import android.app.Application
import android.content.Context
import com.k0dm.atlacharacters.characters.data.CharactersRepository
import com.k0dm.atlacharacters.characters.data.cache.CharactersCacheDataSource
import com.k0dm.atlacharacters.characters.data.cloud.CharactersCloudDataSource
import com.k0dm.atlacharacters.characters.domain.CharactersInteractor
import com.k0dm.atlacharacters.characters.presentation.CharactersRepresentative
import com.k0dm.atlacharacters.characters.presentation.CharactersUiStateObservable
import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor
import com.k0dm.atlacharacters.favorites.presentation.FavoritesRepresentative
import com.k0dm.atlacharacters.favorites.presentation.FavoritesUiStateObservable
import com.k0dm.atlacharacters.main.MainRepresentative
import com.k0dm.atlacharacters.main.Navigation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), ProvideRepresentative {

    private lateinit var factory: ProvideRepresentative

    override fun onCreate() {
        super.onCreate()
        factory = ProvideRepresentative.Factory(ProvideRepresentative.MakeRepresentative(this))
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>) =
        factory.provideRepresentative(clazz)
}

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

    class MakeRepresentative(context: Context) : ProvideRepresentative {

        private val navigation = Navigation.Base()
        private val charactersUiStateObservable = CharactersUiStateObservable.Base()
        private val favoritesUiStateObservable = FavoritesUiStateObservable.Base()

        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(logging).build()
        val retrofit = Retrofit.Builder().baseUrl("https://last-airbender-api.fly.dev")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        val cacheDataSource = CharactersCacheDataSource.Base(context)
        val charactersInteractor = CharactersInteractor.Base(
            repository = CharactersRepository.Base(
                cloudDataSource = retrofit.create(CharactersCloudDataSource::class.java),
                cacheDataSource = cacheDataSource
            )
        )

        private val favoritesInteractor =
            FavoritesInteractor.Base(cacheDataSource = cacheDataSource)

        private val runAsync = RunAsync.Base()


        override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T {

            return when (clazz) {
                MainRepresentative::class.java -> MainRepresentative.Base(navigation)

                CharactersRepresentative::class.java -> CharactersRepresentative.Base(
                    charactersUiStateObservable,
                    charactersInteractor,
                    runAsync
                )

                FavoritesRepresentative::class.java -> FavoritesRepresentative.Base(
                    favoritesUiStateObservable,
                    favoritesInteractor,
                    runAsync
                )

                else -> throw IllegalStateException("No such representative with clazz:$clazz")
            } as T
        }
    }
}