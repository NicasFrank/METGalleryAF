package com.example.metgalleryaf.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.metgalleryaf.navigation.GalleryDestination
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.metgalleryaf.navigation.ItemDestination

@HiltAndroidTest
class MainActivityTest{

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost(){
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(
                    ComposeNavigator()
                )
            }
            MetGalleryApp(navController = navController)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyStartDestination() {
        composeTestRule.onRoot()
            .assertIsDisplayed()
        assertEquals(GalleryDestination.route, navController.currentBackStackEntry?.destination?.route)
        composeTestRule.onNodeWithTag("searchbar")
            .performTextInput("Da Vinci")
        composeTestRule.onNodeWithTag("searchbutton")
            .performClick()
        composeTestRule.waitUntilAtLeastOneExists (hasTestTag("itemcard"), 500000)
        composeTestRule.onAllNodesWithTag("itemcard")[0].performClick()
        assertEquals(ItemDestination.routeWithArgs, navController.currentBackStackEntry?.destination?.route)
    }

}