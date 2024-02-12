package com.k0dm.atlacharacters.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(supportFragmentManager: FragmentManager, container: Int)

    abstract class Replace(private val fragmentClass: Class<out Fragment>): Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager
                .beginTransaction()
                .replace(container, fragmentClass.getDeclaredConstructor().newInstance())
                .commit()
        }
    }

    object Empty : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) = Unit
    }
}