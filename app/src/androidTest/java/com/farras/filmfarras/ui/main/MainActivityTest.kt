package com.farras.filmfarras.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.farras.filmfarras.R
import com.farras.filmfarras.utils.DataDummy
import com.farras.filmfarras.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private val dummyMovie = DataDummy.generateInstrumentationDummyMovie()
    private val dummyTvShow = DataDummy.generateInstrumentationDummyTvShow()

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadTvShows() {
        onView(withText(R.string.tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_title)).check(matches(withText(dummyMovie.title)))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(withText(dummyMovie.storyline)))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText(R.string.tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_title)).check(matches(withText(dummyTvShow.title)))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(withText(dummyTvShow.storyline)))
    }

    @Test
    fun loadFavoriteMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.bn_favorites)).perform(click())
        onView(withId(R.id.rv_movie_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_title)).check(matches(withText(dummyMovie.title)))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(withText(dummyMovie.storyline)))
    }

    @Test
    fun loadFavoriteTvShow() {
        onView(withText(R.string.tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.bn_favorites)).perform(click())
        onView(withText(R.string.favorite_tvshow)).perform(click())
        onView(withId(R.id.rv_tvshow_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_title)).check(matches(withText(dummyTvShow.title)))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_storyline_description)).check(matches(withText(dummyTvShow.storyline)))
    }
}