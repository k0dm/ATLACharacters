package com.k0dm.atlacharacters

import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.CoreMatchers.allOf

class MainPage {

    private val parentLayoutId = R.id.rootLayout

    init {
        onView(
            allOf(
                withId(R.id.container),
                isAssignableFrom(FrameLayout::class.java),
                withParent(withId(parentLayoutId)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.bottomNavigation),
                isAssignableFrom(BottomNavigationView::class.java),
                withParent(withId(parentLayoutId)),
                withParent(isAssignableFrom(LinearLayout::class.java))
            )
        ).check(matches(isDisplayed()))
    }

    fun navigateToFavorites() {
        onView(
            allOf(
                withId(R.id.itemCharacters),
                withText("Characters")
            )
        ).perform(click())
    }

    fun navigateToCharacters() {
        onView(
            allOf(
                withId(R.id.itemFavorites),
                withText("Favorites")
            )
        ).perform(click())
    }
}
