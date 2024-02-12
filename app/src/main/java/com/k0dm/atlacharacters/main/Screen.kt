package com.k0dm.atlacharacters.main

import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(supportFragmentManager: FragmentManager, container: Int)

    object Empty : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) = Unit
    }
}