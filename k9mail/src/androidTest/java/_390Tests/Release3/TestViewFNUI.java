package _390Tests.Release3;

import android.content.Intent;
import android.graphics.Color;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.FollowUpNotificationHolder;
import com.fsck.k9.R;
import com.fsck.k9.activity.FollowUpNotificationsList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItems;

public class TestViewFNUI {
    @Rule
    public ActivityTestRule<FollowUpNotificationsList> followUpNotificationsListActivityTestRule
            = new ActivityTestRule<FollowUpNotificationsList>(FollowUpNotificationsList.class, true, false);
    @Before
    public void setup() {
        Intent i = new Intent();
        i.putExtra("test", true);
        followUpNotificationsListActivityTestRule.launchActivity(i);
    }

    @Test
    public void testFNView() {
        Espresso.onData(any(FollowUpNotificationHolder.class)).atPosition(0).onChildView(withId(R.id.follow_up_time))
                .check(matches(withText("ON: Mar 9, 2099 @ 1:20AM")));

        Espresso.onData(any(FollowUpNotificationHolder.class)).atPosition(0).onChildView(withId(R.id.follow_up_time))
                .check(matches(isDisplayed()));

        Espresso.onData(any(FollowUpNotificationHolder.class)).atPosition(0).onChildView(withId(R.id.recipients))
                .check(matches(withText("TO: Test@hotmail.com")));

        Espresso.onData(any(FollowUpNotificationHolder.class)).atPosition(0).onChildView(withId(R.id.recipients))
                .check(matches(isDisplayed()));

        Espresso.onData(any(FollowUpNotificationHolder.class)).atPosition(0).onChildView(withId(R.id.subject_follow_up))
                .check(matches(withText("SUBJECT: TEST")));
    }
}
