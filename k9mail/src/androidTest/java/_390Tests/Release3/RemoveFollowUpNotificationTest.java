package _390Tests.Release3;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.activity.FollowUpNotificationsList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

public class RemoveFollowUpNotificationTest {
    @Rule
    public ActivityTestRule<FollowUpNotificationsList> activityTestRule = new ActivityTestRule<>(FollowUpNotificationsList.class, true, false);

    private Intent intent;

    @Before
    public void setup(){
        intent = new Intent();
        intent.putExtra("test", true);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void removeFollowUpNotificationTest()
    {
        Espresso.onView(withId(android.R.id.list));
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Delete")).perform(click());
    }
}
