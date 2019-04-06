package _390Tests.Release3;

import android.content.Intent;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.widget.TimePicker;

import com.fsck.k9.R;
import com.fsck.k9.activity.SetDrunkModeTime;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;


public class SetDrunkModeTimeTest {

    @Rule
    public ActivityTestRule<SetDrunkModeTime> activityTestRule = new ActivityTestRule<>(SetDrunkModeTime.class);

    private Intent intent;

    @Before
    public void setUp(){
        intent = new Intent();
        intent.putExtra("testingSetTime", true);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void testChooseNothing(){
        onView(withId(R.id.drunk_mode_set_time_button)).perform(click());
        onView(withText("Time Set!"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testChooseSameTime(){

        int hour = 23;
        int minute = 11;
        String str = 11 + ":" + 11 + " PM";
        onView(withId(R.id.drunk_mode_set_start_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minute));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.drunk_mode_set_end_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minute));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.drunk_mode_start_time)).check(matches(withText(str)));
        onView(withId(R.id.drunk_mode_end_time)).check(matches(withText(str)));

        onView(withId(R.id.drunk_mode_set_time_button)).perform(click());

        onView(withText("Start time and end time can't be the same!"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testChooseDifferentTime(){
        int hourOne = 23;
        int minuteOne = 11;
        String strOne = 11 + ":" + 11 + " PM";
        int hourTwo = 21;
        int minuteTwo = 30;
        String strTwo = 9 + ":" + 30 + " PM";
        onView(withId(R.id.drunk_mode_set_start_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hourOne, minuteOne));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.drunk_mode_set_end_time_button)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hourTwo, minuteTwo));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.drunk_mode_start_time)).check(matches(withText(strOne)));
        onView(withId(R.id.drunk_mode_end_time)).check(matches(withText(strTwo)));

        onView(withId(R.id.drunk_mode_set_time_button)).perform(click());

        onView(withText("Time Set!"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}
