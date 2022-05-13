package com.example.instatask


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMainActivity {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

    @Test
    @Throws(Exception::class)
    fun checkRecyclerViewItems() {
        onView(withId(R.id.recyclerViewItems)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun checkError() {
        onView(withId(R.id.error)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun checkSortToggle() {
        onView(withId(R.id.sortToggle)).check(matches(isDisplayed()))
    }


    @Test
    fun checkSortProcess() {
        onView(withId(R.id.sortToggle)).perform(click())

        val recyclerView =
            activityRule.activity.findViewById(R.id.recyclerViewItems) as RecyclerView
        if (recyclerView.adapter!!.itemCount > 0) {
            onView(withId(R.id.sortToggle)).perform(click())

        }

    }

}