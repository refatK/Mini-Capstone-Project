package com.fsck.k9.activity;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class MessageComposeTest {

    @Rule
    public ActivityTestRule<MessageCompose> mActivityTestRule = new ActivityTestRule<>(MessageCompose.class);

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
    public void testThatMailingListIsAddedCaseInsensitive() {
        ViewInteraction toSection = onView(withId(R.id.to));

        // add text that matches with Mailing Lists in different case
        toSection.perform(scrollTo(), replaceText("firstlist"), closeSoftKeyboard());
        toSection.check(matches(withText("firstlist")));

        // this action should cause the mailing list to be added
        ViewInteraction subject = onView(withId(R.id.subject));
        subject.perform(click());

        // if the mailing list is added, the text should be gone
        toSection.check(matches(not(withText(containsString("firstlist")))));
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

    @Test
    public void testTextThatMatchesAnEmailIsAdded() {
        ViewInteraction toSection = onView(withId(R.id.to));

        // add text that matches an email format
        toSection.perform(scrollTo(), replaceText("example@domain.com"), closeSoftKeyboard());
        toSection.check(matches(withText("example@domain.com")));

        // this should cause the email to be added
        ViewInteraction subject = onView(withId(R.id.subject));
        subject.perform(click());

        // if the email is added, the text should be gone
        toSection.check(matches(not(withText(containsString("example@domain.com")))));
    }

}