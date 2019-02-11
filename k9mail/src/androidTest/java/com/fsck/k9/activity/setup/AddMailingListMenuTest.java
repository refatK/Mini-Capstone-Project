package com.fsck.k9.activity.setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.Description;
import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.Espresso;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.view.View;
import android.widget.ListView;
import android.support.test.rule.ActivityTestRule;
import static org.junit.Assert.*;
import com.fsck.k9.R;

public class AddMailingListMenuTest {

    @Rule
    public ActivityTestRule<MailingListMenu> mActivityTestRule = new ActivityTestRule<MailingListMenu>(MailingListMenu.class);
    private String mailingListName = "mailing list test";
    private String cancelled = "cancelled mailing list";





        @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddMailingList()
    {

        //find total number of items on mailing list menu and record it in a variable
        final int[] listCount = new int[1];


        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                listCount[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }

        }));
        //Test add button
        //click on add button in mailing list
        Espresso.onView(withId(R.id.add_mailing_list)).perform(click());

        //enter mailing list name in edittext
        Espresso.onView(withId(R.id.mailingListName)).perform(typeText(mailingListName));

        //close soft keyboard
        Espresso.closeSoftKeyboard();

        //click add button in dialog
        Espresso.onView(withId(R.id.button_add)).perform(click());

        //check to see that name appears on list
        Espresso.onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(listCount[0]).check(matches(withText(mailingListName)));
    }

    @Test
    public void cancelMailingList()
    {
        //find total number of items on mailing list menu and record it in a variable
        final int[] listCount = new int[1];
        final int[] updatedListCount = new int[1];

        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                listCount[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }

        }));

        //Test cancel button
        //click on add button in mailing list
        Espresso.onView(withId(R.id.add_mailing_list)).perform(click());

        //Enter mailing list name in edit text
        Espresso.onView(withId(R.id.mailingListName)).perform(typeText(cancelled));

        //click on cancel button in dialog
        Espresso.onView(withId(R.id.button_cancel)).perform(click());

        //record total number of elements on list in new variable
        Espresso.onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                updatedListCount [0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }

        }));

        //updated list count before and after cancelling should match
        assertEquals(listCount[0], updatedListCount[0]);
    }

    @After
    public void tearDown() throws Exception {
    }


}