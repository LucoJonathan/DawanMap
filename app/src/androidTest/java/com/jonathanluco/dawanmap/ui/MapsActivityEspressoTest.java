package com.jonathanluco.dawanmap.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.jonathanluco.dawanmap.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class MapsActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MapsActivity> activityRule =
            new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void mapAndListButton_shouldBeDisplayed() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_list)).check(matches(isDisplayed()));
    }

    @Test
    public void listSelection_shouldShowMarkerInfoPanel() throws InterruptedException {
        Thread.sleep(2500);

        onView(withId(R.id.btn_list)).perform(click());
        onView(withText("Sélectionner un centre Dawan"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onData(anything())
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.marker_info_container)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_coordinates)).check(matches(withText(containsString("Latitude"))));
        onView(withId(R.id.tv_coordinates)).check(matches(withText(containsString("Longitude"))));
    }
}
