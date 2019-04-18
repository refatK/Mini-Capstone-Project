package _390Tests.Release3;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.SetFollowUpReminderDateAndTime;
import com.fsck.k9.activity.drunk_mode_challenges.AudioChallenge;

import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
}
