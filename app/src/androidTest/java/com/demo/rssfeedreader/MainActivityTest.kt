package com.demo.rssfeedreader

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun startActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.news_list))
    }

    @Test
    fun clickOnRssFeedItem() {
        Thread.sleep(2000)
        val cardView = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.news_list),
                        childAtPosition(
                            withId(R.id.nav_fragment),
                            0
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        cardView.perform(click())

        Espresso.onView(withId(R.id.news_detail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}