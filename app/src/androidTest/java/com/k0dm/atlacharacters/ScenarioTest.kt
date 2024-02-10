package com.k0dm.atlacharacters

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun success_error_success_add_to_favorite_navigate_to_favorite_and_comeback() {
        val mainPage = MainPage()

        val charactersPage = CharactersPage()
        charactersPage.checkIsDisplayed()
        charactersPage.checkCharacter(
            character = Character(
                name = "Katara",
                allies = "Team Avatar",
                enemies = "Fire Nation",
                affiliation = "Team Avatar"
            )
        )

        charactersPage.clickNext()
        charactersPage.checkError(error = "Service unavailable")

        charactersPage.clickRetry()
        charactersPage.checkCharacter(
            character = Character(
                name = "Aang",
                allies = "Team Avatar",
                enemies = "Firelord Ozai, Azula",
                affiliation = "Team Avatar"
            )
        )

        charactersPage.addToFavorite()
        charactersPage.checkAddedToFavorite()

        mainPage.navigateToFavorites()
        charactersPage.checkNotDisplayed()

        val favoritesPage = FavoritesPage()
        favoritesPage.checkIsDisplayed()
        favoritesPage.checkCollapsedCharacter(position = 0, name = "Aang")

        favoritesPage.clickItem(position = 0)
        favoritesPage.checkExpandedCharacter(
            position = 0, character = Character(
                name = "Aang",
                allies = "Team Avatar",
                enemies = "Firelord Ozai, Azula",
                affiliation = "Team Avatar"
            )
        )

        favoritesPage.clickCollapseButton(position = 0)
        favoritesPage.checkCollapsedCharacter(position = 0, name = "Aang")

        mainPage.navigateToCharacters()
        favoritesPage.checkNotDisplayed()
        charactersPage.checkIsDisplayed()

        charactersPage.checkCharacter(
            character = Character(
                name = "Aang",
                allies = "Team Avatar",
                enemies = "Firelord Ozai, Azula",
                affiliation = "Team Avatar"
            )
        )
        charactersPage.checkAddedToFavorite()
    }
}

internal class Character(
    val name: String,
    val allies: String,
    val enemies: String,
    val affiliation: String
)