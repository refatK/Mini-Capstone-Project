package _390Tests.Release2;

import android.support.test.espresso.Espresso;

import org.junit.Rule;
import org.junit.Test;

import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.activity.setup.QuickRepliesMenu;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

public class RemoveQuickReplyTest {
    @Rule
    public ActivityTestRule<QuickRepliesMenu> testRule = new ActivityTestRule<QuickRepliesMenu>(QuickRepliesMenu.class);

    @Test
    public void removeQuickReplyTest()
    {
        Espresso.onView(withId(android.R.id.list));
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Delete")).perform(click());
    }
}
