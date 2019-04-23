package _390Tests.Release3;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.app.Activity;
import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

import static com.fsck.k9.K9.daoSession;
import static org.mockito.Mockito.*;

import com.fsck.k9.activity.FollowUpNotificationsList;
import com.fsck.k9.activity.SetFollowUpReminderDateAndTime;

import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

public class EditFollowUpNotificationTest extends InstrumentationTestCase {
    @Rule
    public ActivityTestRule<FollowUpNotificationsList> activityTestRule = new ActivityTestRule<>(FollowUpNotificationsList.class, true, false);

    private Intent intent;
    private Calendar calendar;

    @Before
    public void setup(){
        intent = new Intent();
        intent.putExtra("test", true);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void editFollowUpNotificationTest()
    {
        Espresso.onView(withId(android.R.id.list));
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Reschedule")).perform(click());
    }
    @Test
    public void onActivityResultTest(){

        calendar = Calendar.getInstance();
        Intent mockIntent = mock(Intent.class);

        when(mockIntent.getLongExtra("fNId",1L)).thenReturn(0L);
        when(mockIntent.getLongExtra("newFollowUpReminderDate",1L)).thenReturn(calendar.getTimeInMillis());

        Instrumentation.ActivityResult activityResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, mockIntent);
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(SetFollowUpReminderDateAndTime.class.getName(), activityResult , true);

        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Reschedule")).perform(click());

        assertEquals(daoSession.getFollowUpReminderEmailDao().load(0L).getReminderDateTime(),(Long)calendar.getTimeInMillis());
    }
}
