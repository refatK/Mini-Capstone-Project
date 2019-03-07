package com.fsck.k9.activity;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import com.fsck.k9.R;

import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

public class SetDateAndTimeTest {
    @Rule
    public ActivityTestRule<SetDateAndTime> testRule = new ActivityTestRule<>(SetDateAndTime.class);

    private String strDate;
    private String strTime;

    @Test
    public void testChooseNothing(){
        strDate = "MM/DD/YYYY";
        strTime = "hh:mm";
        Espresso.onView(withId(R.id.send_later_set_date_and_time_button)).perform(click());
        Espresso.onView(withId(R.id.send_later_date)).check(matches(withText(strDate)));
        Espresso.onView(withId(R.id.send_later_time)).check(matches(withText(strTime)));
    }

    @Test
    public void testChooseNow(){
    }

    @Test
    public void testChooseBeforeNow(){

    }

    @Test
    public void testChooseAfterNow(){

    }
}
