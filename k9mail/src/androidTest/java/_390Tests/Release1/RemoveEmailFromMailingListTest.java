package _390Tests.Release1;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ListView;

import com.fsck.k9.activity.setup.MailingListEmailListMenu;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

public class RemoveEmailFromMailingListTest {

    @Rule
    public ActivityTestRule<MailingListEmailListMenu> testRule = new ActivityTestRule<MailingListEmailListMenu>(MailingListEmailListMenu.class, false, false);

    @Before
    public void setup(){
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putBoolean("testToggle", true);

        i.putExtras(b);

        testRule.launchActivity(i);
    }

    @Test
    public void testRemoveEmail(){
        // For some reason, counter can't be a variable
        final int[] counter = new int[1];
        final int[] newCounter = new int[1];

        onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
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
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        onView(allOf(withId(android.R.id.title), withText("Delete"))).perform(click());
        onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>(){
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
        System.out.println(newCounter[0]);
        System.out.println(counter[0]);
        assertThat(newCounter[0], is(counter[0]-1));
    }
}
