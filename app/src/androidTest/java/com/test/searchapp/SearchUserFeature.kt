package com.test.searchapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.searchapp.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchUserFeature {

    @Before
    fun setup() {
        val activity = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testOpenFragmentUserList() {
        onView(withId(R.id.fragment_user_list)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.list_user)).check(matches(isDisplayed()))
    }
}