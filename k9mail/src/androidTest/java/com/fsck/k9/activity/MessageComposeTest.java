package com.fsck.k9.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ListView;

import com.fsck.k9.R;
import com.fsck.k9.activity.setup.MailingListMenu;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class MessageComposeTest {

    @Rule
    public ActivityTestRule<MessageCompose> mActivityTestRule = new ActivityTestRule<>(MessageCompose.class);
    private String mailingListName = "mailing list test";
    private String cancelled = "cancelled mailing list";


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testThatMailingListIsAdded() {
        ViewInteraction toSection = onView(withId(R.id.to));

        // add text that matches with Mailing Lists
        toSection.perform(scrollTo(), replaceText("FirstList"), closeSoftKeyboard());
        toSection.check(matches(withText("FirstList")));

        // this action should cause the mailing list to be added
        ViewInteraction subject = onView(withId(R.id.subject));
        subject.perform(click());

        // if the mailing list is added, the text should be gone
        toSection.check(matches(not(withText(containsString("FirstList")))));
    }

    @Test
    public void testTextThatDoesNotMatchAMailingListIsNotAdded() {
        ViewInteraction toSection = onView(withId(R.id.to));

        // add text that doesn't match email or an existing mailing list
        toSection.perform(scrollTo(), replaceText("randomText"), closeSoftKeyboard());
        toSection.check(matches(withText("randomText")));

        // this action should cause error that leaves the text there
        ViewInteraction subject = onView(withId(R.id.subject));
        subject.perform(click());

        // text should stay since the text shouldn't add anything
        toSection.check(matches(withText("randomText")));
    }

    @Ignore
    @Test
    public void testAddMailingList() {

        //find total number of items on mailing list menu and record it in a variable
        final int[] listCount = new int[1];


        onView(withId(android.R.id.list)).check(matches(new TypeSafeMatcher<View>() {
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
        onView(withId(R.id.add_mailing_list)).perform(click());

        //enter mailing list name in edittext
        onView(withId(R.id.mailingListName)).perform(typeText(mailingListName));

        //close soft keyboard
        Espresso.closeSoftKeyboard();

        //click add button in dialog
        onView(withId(R.id.button_add)).perform(click());

        //check to see that name appears on list
        Espresso.onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(listCount[0]).check(matches(withText(mailingListName)));
    }


    @After
    public void tearDown() throws Exception {
    }


}