package _390Tests.Release3;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.SetFollowUpReminderDateAndTime;
import com.fsck.k9.activity.drunk_mode_challenges.AudioChallenge;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SetFollowUpReminderDateAndTimeTests {

    @Rule
    public ActivityTestRule<SetFollowUpReminderDateAndTime> testRule = new ActivityTestRule<SetFollowUpReminderDateAndTime>(SetFollowUpReminderDateAndTime.class);

    private String strDate;
    private String strTime;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private Intent intent;

    @Before
    public void initialize(){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        intent = new Intent();
        intent.putExtra("testingSetFollowUpReminderDateAndTime", true);
        testRule.launchActivity(intent);
    }

    @Test
    public void selectNothing(){
        strDate = "MM/DD/YYYY";
        strTime = "hh:mm";
        onView(withId(R.id.reminder_set_date_and_time_button)).perform(click());
        onView(withId(R.id.reminder_date)).check(matches(withText(strDate)));
        onView(withId(R.id.reminder_time)).check(matches(withText(strTime)));
    }
}
