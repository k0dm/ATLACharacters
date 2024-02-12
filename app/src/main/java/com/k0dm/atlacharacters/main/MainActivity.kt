package com.k0dm.atlacharacters.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.k0dm.atlacharacters.R
import com.k0dm.atlacharacters.core.ProvideRepresentative
import com.k0dm.atlacharacters.core.Representative
import com.k0dm.atlacharacters.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideRepresentative {

    private lateinit var representative: MainRepresentative
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        representative.stopGettingUpdates(NavigationObserver.Empty)
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<out T>): T {
        return (application as ProvideRepresentative).provideRepresentative(clazz)
    }
}

