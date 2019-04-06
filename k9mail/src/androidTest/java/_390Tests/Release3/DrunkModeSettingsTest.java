package _390Tests.Release3;

import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.setup.DrunkModeSettings;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.PreferenceMatchers.withKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class DrunkModeSettingsTest {

    @Rule
    public ActivityTestRule<DrunkModeSettings> mActivityTestRule = new ActivityTestRule<>(DrunkModeSettings.class);

    //Check if option is displayed
    @Test
    public void testSetDrunkTime(){
        onData(withKey("drunk_mode_settings_time")).check(matches(isDisplayed()));
    }

    //Check if toggle displayed
    @Test
    public void testToggleDrunk(){
        onData(withKey("drunk_mode_settings_toggle")).check(matches(isDisplayed()));
    }

    //Check if toggle works for one click
    @Test
    public void testToggleDrunkOnce(){
        onData(withKey("drunk_mode_settings_toggle")).perform(click());
    }

    //Check if toggle works for two clicks
    @Test
    public void testToggleDrunkTwice(){
        onData(withKey("drunk_mode_settings_toggle")).perform(click());
        onData(withKey("drunk_mode_settings_toggle")).perform(click());
    }
}
