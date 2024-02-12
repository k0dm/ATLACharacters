package com.k0dm.atlacharacters.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.k0dm.atlacharacters.R
import com.k0dm.atlacharacters.characters.presentation.CharactersScreen
import com.k0dm.atlacharacters.core.ProvideRepresentative
import com.k0dm.atlacharacters.core.Representative
import com.k0dm.atlacharacters.databinding.ActivityMainBinding
import com.k0dm.atlacharacters.favorites.presentation.FavoritesScreen

class MainActivity : AppCompatActivity(), ProvideRepresentative {

    private lateinit var representative: MainRepresentative
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemReselectedListener { item->
            when(item.itemId) {
                R.id.itemCharacters -> representative.navigate(CharactersScreen)
                R.id.itemFavorites -> representative.navigate(FavoritesScreen)
            }
        }

        representative.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(object : NavigationObserver {
            override fun updateUi(uiState: Screen) {
                uiState.show(supportFragmentManager, R.id.container)
                representative.notifyObserved()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T {
        return (application as ProvideRepresentative).provideRepresentative(clazz)
    }
}

