package com.fsck.k9.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fsck.k9.Account;
import com.fsck.k9.R;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.mail.Folder;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScheduledFolderTest {

    private String to;
    private String subject;
    private String body;

    @Rule
    public ActivityTestRule<Accounts>
            accountsActivityTestRule = new ActivityTestRule<>(Accounts.class);
    @Before
    public void initTests(){
        to = "test@abc.xyz";
        subject = "Testing Mail";
        body = "This is my Test Email!";
    }

    @Test
    public void seeDateAndTimeInScheduledFolder() throws InterruptedException{
        onView(withId(R.id.compose)).perform(click());

        onView(withId(R.id.to)).perform(typeText(to));
        onView(withId(R.id.subject)).perform(typeText(subject));
        onView(withId(R.id.message_content)).perform(typeText(body));
        onView(withId(R.id.send_later)).perform(click());

        onView(withId(R.id.send_later_set_date_button)).perform(click());

        onView(withClassName((Matchers.equalTo(DatePicker.class.getName()))))
                .perform(PickerActions.setDate(2100, 1 + 1, 1));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_set_time_button)).perform(click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(1, 23));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.send_later_set_date_and_time_button)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getContext());

        onView(withText("Folders")).perform(click());

        onData(anything())
                .inAdapterView(withContentDescription("Scheduled")).perform(click());

    }
}
