package com.fsck.k9.activity.setup;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.Description;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.support.test.rule.ActivityTestRule;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import com.fsck.k9.Account;
import com.fsck.k9.R;
import com.fsck.k9.activity.ChooseFolder;
import com.fsck.k9.activity.FolderInfoHolder;
import com.fsck.k9.activity.FolderList;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.activity.MessageList;
import com.fsck.k9.fragment.ScheduledMailAdapter;


public class EditScheduledMessageUiTest{

    @Rule
    public ActivityTestRule<MessageList> mActivityTestRule = new ActivityTestRule<MessageList>(MessageList.class);
    public String bodyText = "edited";
    private String cancelledString = "cancelled";

    @Before
    public void setUp() throws Exception {
        //this method should perform send email to scheduled folder (please do that for other ui tests)
        //but temporarily copies draft message to scheduled folder for editing test purposes

        //compose a message, save it as a draft and copy to scheduled folder

        //tap compose message button
        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.compose), withContentDescription("Compose"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                        0),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        //type subject name in subject and close keyboard
        ViewInteraction editText4 = onView(
                allOf(withId(R.id.subject),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0)));
        editText4.perform(scrollTo(), replaceText("test subject"), closeSoftKeyboard());

        //type email in to: and close keyboard
        ViewInteraction recipientSelectView = onView(
                allOf(withId(R.id.to),
                        childAtPosition(
                                allOf(withId(R.id.to_wrapper),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                1)));
        recipientSelectView.perform(scrollTo(), replaceText("test@example.com"), closeSoftKeyboard());

        //navigate back to tap save as draft
        ViewInteraction linearLayout3 = onView(
                allOf(withContentDescription("Compose, Navigate up"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ActionBarView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                                0)),
                                0),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        button5.perform(click());

        //click on folder icon next to account name
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.folders),
                        childAtPosition(
                                allOf(withId(R.id.folder_button_wrapper),
                                        childAtPosition(
                                                withId(R.id.accounts_item_layout),
                                                3)),
                                1),
                        isDisplayed()));
        imageButton.perform(click());

        //tap drafts folder
        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(1);
        linearLayout.perform(click());

        //longtap first message in drafts
        DataInteraction linearLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.message_list),
                        childAtPosition(
                                withId(R.id.swiperefresh),
                                0)))
                .atPosition(0);
        linearLayout6.perform(longClick());



        //tap and copy
        onView(
                allOf(withId(android.R.id.title), withText("Copy"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed())).perform(ViewActions.scrollTo())
                .perform(click());


        //select scheduled folder
        DataInteraction textView3 = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)))
                .atPosition(9);
        textView3.perform(click());

        //back to folders page
        ViewInteraction linearLayout4 = onView(
                allOf(withContentDescription("K-9 Mail, Navigate up"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ActionBarView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                                0)),
                                0),
                        isDisplayed()));
        linearLayout4.perform(click());
    }



    @Test
    public void testEditedAndSavedMessage(){

        //tap on scheduled folder
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(11).
                perform(click());


        //tap first message in scheduled folder
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(R.id.message_list),
                        childAtPosition(
                                withId(R.id.swiperefresh),
                                0)))
                .atPosition(0).
                perform(click());

        //focus on message body
        Espresso.onView(
                allOf(withId(R.id.message_content),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4))).
                perform(scrollTo(), click());

        //type string text in body and close soft keyboard
        Espresso.onView(
                allOf(withId(R.id.message_content),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4))).
                perform(scrollTo(), replaceText(bodyText), closeSoftKeyboard());

        //tap save scheduled button
        Espresso.onView(
                allOf(withId(R.id.save_scheduled), withContentDescription("Save scheduled message"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarView")),
                                        2),
                                1),
                        isDisplayed())).
                perform(click());


        //tap on same message after saving
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(R.id.message_list),
                        childAtPosition(
                                withId(R.id.swiperefresh),
                                0)))
                .atPosition(0).
                perform(click());


        //verify that body contains edited text string
        Espresso.onView(
                allOf(withId(R.id.message_content),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4))).
                check(matches(withText(bodyText)));
    }

    @Test
    public void testEditedAndCanceledMessage(){
        //tap on scheduled folder
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(11).
                perform(click());


        //tap first message in scheduled folder
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(R.id.message_list),
                        childAtPosition(
                                withId(R.id.swiperefresh),
                                0)))
                .atPosition(0).
                perform(click());


        //focus on message body
        Espresso.onView(
                allOf(withId(R.id.message_content),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4))).
                perform(scrollTo(), click());

        //replace empty body with cancelled message
        Espresso.onView(
                allOf(withId(R.id.message_content),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4))).
                perform(scrollTo(), replaceText(cancelledString), closeSoftKeyboard());

        //tap back button to open discard prompt
        Espresso.onView(
                allOf(withContentDescription("Compose, Navigate up"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ActionBarView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                                0)),
                                0),
                        isDisplayed()))
                .perform(click());

        //tap discard in dialog (should return to scheduled page with scheduled message not deleted)

        Espresso.onView(withId(android.R.id.button2)).perform(click());

        //go back to folder list
        Espresso.onView(
                allOf(withContentDescription("K-9 Mail, Navigate up"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ActionBarView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                                0)),
                                0),
                        isDisplayed())).
                perform(click());



        //click on scheduled folder
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(11).
                perform(click());


        //tap on same message after saving
        Espresso.onData(anything())
                .inAdapterView(allOf(withId(R.id.message_list),
                        childAtPosition(
                                withId(R.id.swiperefresh),
                                0)))
                .atPosition(0).
                perform(click());


        //click on same message, verify that body contains old string
        Espresso.onView(
                allOf(withId(R.id.message_content),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4))).
                check(matches(not((withText(cancelledString)))));



    }





    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }



    @After
    public void tearDown() throws Exception {
    }



}