package com.fsck.k9.activity.setup;


import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import com.fsck.k9.R;
import com.fsck.k9.activity.setup.MailingListEmailListMenu;

public class RemoveEmailAddressTest {

    @Rule
    public ActivityTestRule<MailingListEmailListMenu> testRule = new ActivityTestRule<MailingListEmailListMenu>(MailingListEmailListMenu.class);
    private String correctEmail = "test1@test.com";

    @Test
    public void removeEmailAddressTest()
    {
        addEmail();
        Espresso.onView(withId(android.R.id.list));
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Delete")).perform(click());
    }

    public void addEmail(){

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
        Espresso.onView(withId(R.id.mailing_list_add_email)).perform(click());
        Espresso.onView(withId(R.id.emailAddress)).perform(typeText(correctEmail));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_add)).perform(click());
        Espresso.onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(counter[0]).check(matches(withText(correctEmail)));
    }
}
