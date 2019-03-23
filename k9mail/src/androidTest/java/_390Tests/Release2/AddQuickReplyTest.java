package _390Tests.Release2;

import android.support.test.espresso.Espresso;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
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

public class AddQuickReplyTest {
    /*
     * The following are tested:
     *
     * Clicking the add button to add an email
     * Typing the right email, then clicking add
     * Typing the wrong email, then clicking add
     * Clicking on cancel
     *
     */

    @Rule
    public ActivityTestRule<QuickRepliesMenu> testRule = new ActivityTestRule<QuickRepliesMenu>(QuickRepliesMenu.class);
    private String quickReply = "Bye";
    private String emptyString = "";

    @Test
    public void testAddReply(){
        // For some reason, counter can't be a variable
        final int[] counter = new int[1];

        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                counter[0] = listView.getAdapter().getCount();
                return true;
            }
        }));
        Espresso.onView(withId(R.id.add_quick_reply)).perform(click());
        Espresso.onView(withId(R.id.quickReply)).perform(typeText(quickReply));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_add)).perform(click());
        Espresso.onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(counter[0]).check(matches(withText(quickReply)));
    }

    @Test
    public void testNoReply(){
        // For some reason, counter can't be a variable
        final int[] counter = new int[1];
        final int[] newCounter = new int[1];

        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                counter[0] = listView.getAdapter().getCount();
                return true;
            }
        }));
        Espresso.onView(withId(R.id.add_quick_reply)).perform(click());
        Espresso.onView(withId(R.id.quickReply)).perform(typeText(emptyString));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_add)).perform(click());

        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                newCounter[0] = listView.getAdapter().getCount();
                return true;
            }
        }));
        assertThat(newCounter[0], is(counter[0]));
    }

    @Test
    public void testCancel(){
        // For some reason, counter can't be a variable
        final int[] counter = new int[1];
        final int[] newCounter = new int[1];

        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                counter[0] = listView.getAdapter().getCount();
                return true;
            }
        }));
        Espresso.onView(withId(R.id.add_quick_reply)).perform(click());
        Espresso.onView(withId(R.id.button_cancel)).perform(click());

        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                newCounter[0] = listView.getAdapter().getCount();
                return true;
            }
        }));
        assertThat(newCounter[0], is(counter[0]));
    }
}
