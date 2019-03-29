package _390Tests.Release2;

import android.support.test.espresso.Espresso;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import android.support.test.rule.ActivityTestRule;
import com.fsck.k9.R;
import com.fsck.k9.activity.setup.QuickRepliesMenu;

import static android.support.test.espresso.action.ViewActions.longClick;


public class UpdateQuickReplyTest {

    @Rule
    public ActivityTestRule<QuickRepliesMenu> testRule = new ActivityTestRule<>(QuickRepliesMenu.class);
    private String editedReply = "Edited";


    /**
     * test performed to check if edited quick reply is updated
     */
    @Test
    public void testAddReply() {
        Espresso.onView(withId(android.R.id.list));

        Espresso.onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());

        Espresso.onView(withText("Edit")).perform(click());

        Espresso.onView(withId(R.id.quickReply)).perform(typeText(editedReply));

        Espresso.onView(withText("Save")).perform(click());

        Espresso.closeSoftKeyboard();

        Espresso.onData(Matchers.anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).check(matches(withText(editedReply)));


    }







}