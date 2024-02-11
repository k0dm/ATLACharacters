package com.k0dm.atlacharacters

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.recyclerviewmatcher.ImageViewDrawableMatcher
import com.google.android.material.button.MaterialButton
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class CharactersPage {

    private val characterLayoutId = R.id.characterLayout
    private val characterLayout = onView(
        allOf(
            withId(characterLayoutId),
            isAssignableFrom(LinearLayout::class.java),
            withParent(withId(R.id.container))
        )
    )
    private val favoriteImageView = onView(
        allOf(
            withId(R.id.favoriteImageView),
            isAssignableFrom(ImageView::class.java),
            withParent(withId(characterLayoutId))
        )
    )
    private val contentLayoutId = R.id.contentLayout
    private val contentLayout = onView(
        allOf(
            withId(contentLayoutId),
            isAssignableFrom(LinearLayout::class.java),
            withParent(withId(characterLayoutId))
        )
    )
    private val actionButton = onView(
        allOf(
            withId(R.id.actionButton),
            isAssignableFrom(MaterialButton::class.java),
            withParent(withId(characterLayoutId))
        )
    )

    fun checkIsDisplayed() {
        characterLayout.check(matches(isDisplayed()))
        favoriteImageView.check(matches(isDisplayed()))
        contentLayout.check(matches(isDisplayed()))
        actionButton.check(matches(withText("Next")))
    }

    fun checkNotDisplayed() {
        characterLayout.check(matches(not(isDisplayed())))
    }

    fun checkCharacter(character: Character) {
        onView(
            allOf(
                withId(R.id.nameTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(contentLayoutId))
            )
        ).check(matches(withText(character.name)))

        onView(
            allOf(
                withId(R.id.characterImageView),
                isAssignableFrom(ImageView::class.java),
                withParent(withId(contentLayoutId))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.alliesTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(contentLayoutId))
            )
        ).check(matches(withText(character.allies)))

        onView(
            allOf(
                withId(R.id.enemiesTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(contentLayoutId))
            )
        ).check(matches(withText(character.enemies)))

        onView(
            allOf(
                withId(R.id.affiliationTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(contentLayoutId))
            )
        ).check(matches(withText(character.affiliation)))
    }

    fun clickNext() {
        actionButton.perform(click())
    }

    fun checkError(error: String) {
        onView(
            allOf(
                withId(R.id.titleTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(characterLayoutId)),
                withText("ATLA Characters")
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.errorTextView),
                isAssignableFrom(TextView::class.java),
                withParent(withId(characterLayoutId)),
                withText(error)
            )
        ).check(matches(isDisplayed()))

        actionButton.check(matches(withText("Retry")))
    }

    fun clickRetry() {
        actionButton.perform(click())
    }

    fun checkAddedToFavorite() {
        favoriteImageView.check(matches(ImageViewDrawableMatcher(R.drawable.favorite)))
    }

    fun addToFavorite() {
        favoriteImageView.perform(click())
    }
}
