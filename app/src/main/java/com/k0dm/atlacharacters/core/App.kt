package com.k0dm.atlacharacters.core

import android.app.Application
import android.content.Context
import com.k0dm.atlacharacters.characters.data.CharactersRepository
import com.k0dm.atlacharacters.characters.data.cache.CharactersCacheDataSource
import com.k0dm.atlacharacters.characters.data.cloud.CharactersCloudDataSource
import com.k0dm.atlacharacters.characters.domain.CharactersInteractor
import com.k0dm.atlacharacters.characters.presentation.CharactersUiStateObservable
import com.k0dm.atlacharacters.favorites.domain.FavoritesInteractor
import com.k0dm.atlacharacters.favorites.presentation.FavoritesUiStateObservable
import com.k0dm.atlacharacters.main.Navigation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), ProvideRepresentative {

    private lateinit var factory: ProvideRepresentative

    override fun onCreate() {
        super.onCreate()
        factory = ProvideRepresentative.Factory(ProvideRepresentative.MakeRepresentative(Core.Base(this)))
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>) =
        factory.provideRepresentative(clazz)
}

interface Core {

    fun navigation(): Navigation.Mutable

    fun charactersUiStateObservable(): CharactersUiStateObservable

    fun favoritesUiStateObservable(): FavoritesUiStateObservable

    fun runAsync(): RunAsync

    fun charactersInteractor(): CharactersInteractor

    fun favoritesInteractor(): FavoritesInteractor

    abstract class Abstract(): Core {

        private val navigation = Navigation.Base()
        private val charactersUiStateObservable = CharactersUiStateObservable.Base()
        private val favoritesUiStateObservable = FavoritesUiStateObservable.Base()
        private val runAsync = RunAsync.Base()

        override fun navigation() = navigation

        override fun charactersUiStateObservable() = charactersUiStateObservable

        override fun favoritesUiStateObservable() = favoritesUiStateObservable

        override fun runAsync() = runAsync
    }

    class Base(context: Context): Abstract() {

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
        private val favoritesInteractor = FavoritesInteractor.Base(cacheDataSource = cacheDataSource)

        override fun charactersInteractor() = charactersInteractor

        override fun favoritesInteractor() = favoritesInteractor
    }

    class Mock() : Abstract() {


        override fun charactersInteractor() = CharactersInteractor.MockForUiTests()

        override fun favoritesInteractor(): FavoritesInteractor {
            TODO("Not yet implemented")
        }
    }
}