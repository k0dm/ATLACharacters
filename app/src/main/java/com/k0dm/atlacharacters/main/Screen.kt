package com.k0dm.atlacharacters.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(supportFragmentManager: FragmentManager, container: Int)

    abstract class Replace(private val fragmentClass: Class<out Fragment>) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager
                .beginTransaction()
                .replace(container, fragmentClass.getDeclaredConstructor().newInstance())
                .commit()
        }
    }

    class ShowAndHide(
        private val newFragment: Class<out Fragment>,
        private val oldFragment: Class<out Fragment>
    ) : Screen {

        override fun show(
            supportFragmentManager: FragmentManager,
            container: Int
        ) = with(supportFragmentManager) {

            val fragmentToShow = findFragmentByTag(newFragment.name) ?: newFragment
                .getDeclaredConstructor().newInstance().also { fragment ->
                    beginTransaction().add(container, fragment, newFragment.name).commit()
                }

            val fragmentToHide = findFragmentByTag(oldFragment.name) ?: oldFragment
                .getDeclaredConstructor().newInstance().also { fragment ->
                    beginTransaction().add(container, fragment, oldFragment.name).commit()
                }

            beginTransaction()
                .show(fragmentToShow)
                .hide(fragmentToHide)
                .commit()
            Unit
        }
    }

    object Empty : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) = Unit
    }
}