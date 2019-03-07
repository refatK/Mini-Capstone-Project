package com.fsck.k9.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;
import android.widget.TimePicker;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import com.fsck.k9.R;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

public class SetDateAndTimeTest {
    @Rule
    public ActivityTestRule<SetDateAndTime> activityTestRule = new ActivityTestRule<>(SetDateAndTime.class);

    private String strDate;
    private String strTime;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Before
    public void setUp(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    @Test
    public void testChooseNothing(){
        strDate = "MM/DD/YYYY";
        strTime = "hh:mm";
        onView(withId(R.id.send_later_set_date_and_time_button)).perform(click());
        onView(withId(R.id.send_later_date)).check(matches(withText(strDate)));
        onView(withId(R.id.send_later_time)).check(matches(withText(strTime)));
    }

    @Test
    public void testChooseNow(){
        strDate = (month + 1) + "/" + day + "/" + year;
        strTime = (hour%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hour > 12 ? "PM" : "AM");

        onView(withId(R.id.send_later_set_date_button)).perform(click());
        onView(withClassName((Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(year, month + 1, day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_set_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minute));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_date)).check(matches(withText(strDate)));
        onView(withId(R.id.send_later_time)).check(matches(withText(strTime)));

        onView(withId(R.id.send_later_set_date_and_time_button)).perform(click());
        onView(withText("Pick another date"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testChooseBefore(){
        strDate = (month + 1) + "/" + day + "/" + (year-1);
        strTime = (hour%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hour > 12 ? "PM" : "AM");

        onView(withId(R.id.send_later_set_date_button)).perform(click());
        onView(withClassName((Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(year-1, month + 1, day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_set_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minute));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_date)).check(matches(withText(strDate)));
        onView(withId(R.id.send_later_time)).check(matches(withText(strTime)));

        onView(withId(R.id.send_later_set_date_and_time_button)).perform(click());
        onView(withText("Pick another date"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testChooseAfter(){
        strDate = (month + 1) + "/" + day + "/" + (year+1);
        strTime = (hour%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hour > 12 ? "PM" : "AM");

        onView(withId(R.id.send_later_set_date_button)).perform(click());
        onView(withClassName((Matchers.equalTo(DatePicker.class.getName())))).perform(PickerActions.setDate(year+1, month + 1, day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_set_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minute));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_date)).check(matches(withText(strDate)));
        onView(withId(R.id.send_later_time)).check(matches(withText(strTime)));

        onView(withId(R.id.send_later_set_date_and_time_button)).perform(click());
        String strDateAndTime = "Setting time to: " + strDate + " @ " + strTime;
        onView(withText(strDateAndTime))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

}
