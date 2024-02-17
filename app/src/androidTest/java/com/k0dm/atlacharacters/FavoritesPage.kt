package com.k0dm.atlacharacters

import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recyclerviewmatcher.RecyclerViewMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

internal class FavoritesPage {

    private val favoritesLayoutId = R.id.favoritesLayout
    private val favoritesLayout = onView(
        allOf(
            withId(favoritesLayoutId),
            isAssignableFrom(LinearLayout::class.java),
            withParent(withId(R.id.container))
        )
    )
    private val recyclerViewId = R.id.favoritesTitleTextView
    private val contentLayoutId = R.id.contentLayout

    fun checkIsDisplayed() {
        favoritesLayout.check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.favoritesTitleTextView),
                isAssignableFrom(TextView::class.java),
                withText("Favorite characters"),
                withParent(withId(favoritesLayoutId))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(recyclerViewId),
                isAssignableFrom(RecyclerView::class.java),
                withParent(withId(favoritesLayoutId))
            )
        ).check(matches(isDisplayed()))
    }

    fun checkNotDisplayed() {
        favoritesLayout.check(matches(not(isDisplayed())))
    }

    fun checkCollapsedCharacter(position: Int, name: String) {
        onView(
            RecyclerViewMatcher(recyclerViewId, position, R.id.favoriteCharacterCollapsedLayout),
        ).check(matches(isDisplayed()))

        onView(
            RecyclerViewMatcher(recyclerViewId, position, R.id.characterNameTextView),
        ).check(matches(withText(name)))
    }

    fun clickItem(position: Int) {
        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.favoriteCharacterCollapsedLayout))
            .perform(click())
    }

    fun checkExpandedCharacter(position: Int, character: Character) {
        onView(RecyclerViewMatcher(recyclerViewId, position, contentLayoutId))
            .check(matches(isDisplayed()))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.nameTextView))
            .check(matches(withText(character.name)))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.characterImageView))
            .check(matches(isDisplayed()))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.alliesTextView))
            .check(matches(withText(character.allies)))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.enemiesTextView))
            .check(matches(withText(character.enemies)))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.affiliationTextView))
            .check(matches(withText(character.affiliation)))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.collapseImageView))
            .check(matches(isDisplayed()))

        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.favoriteImageView))
            .check(matches(isDisplayed()))
    }

    fun clickCollapseButton(position: Int) {
        onView(RecyclerViewMatcher(recyclerViewId, position, R.id.favoriteImageView))
            .perform(click())
    }
}