package _390Tests.Release1;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.setup.MailingListMenu;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

public class RenameMailingListTest {

    @Rule
    public ActivityTestRule<MailingListMenu> testRule = new ActivityTestRule<MailingListMenu>(MailingListMenu.class);
    private String newName = "New Mailing List Name";

    @Test
    public void renameMailingListTest()
    {
        Espresso.onView(withId(android.R.id.list));
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Rename")).perform(click());
        Espresso.onView(withId(R.id.mailingListName)).perform(typeText(newName));
        Espresso.onView(withText("Rename")).perform(click());
        Espresso.closeSoftKeyboard();
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(longClick());
        Espresso.onView(withText("Rename")).perform(click());
        Espresso.onView(withText("Cancel")).perform(click());
    }
}