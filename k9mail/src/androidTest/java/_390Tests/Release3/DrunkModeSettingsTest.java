package _390Tests.Release3;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.setup.DrunkModeSettings;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.PreferenceMatchers.withKey;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static org.hamcrest.CoreMatchers.not;

public class DrunkModeSettingsTest {

    @Rule
    public ActivityTestRule<DrunkModeSettings> activityTestRule = new ActivityTestRule<>(DrunkModeSettings.class);

    private Intent intent;
    private final int MILLIS_DELAY = 1500;

    @Before
    public void setUp(){
        intent = new Intent();
        intent.putExtra("testingSettings", true);
        activityTestRule.launchActivity(intent);
    }

    //Makes sure the toggles work
    @Test
    public void testToggleDrunk(){
        onData(withKey("drunk_mode_settings_time")).check(matches(isDisplayed()));
        onData(withKey("drunk_mode_settings_toggle")).check(matches(isDisplayed()));
        onData(withKey("drunk_mode_settings_toggle")).perform(click());
        onData(withKey("drunk_mode_settings_toggle")).perform(click());
    }

    //Makes sure that state corresponds to the toggle's state
    @Test
    public void testToggleWhenUnchecked(){
        boolean checked = activityTestRule.getActivity().isDrunkChecked();
        SystemClock.sleep(MILLIS_DELAY);
        if(checked){
            onData(withKey("drunk_mode_settings_time")).check(matches(not(isEnabled())));
            onData(withKey("drunk_mode_settings_toggle")).perform(click());
        }
        onData(withKey("drunk_mode_settings_toggle")).check(matches(not(isChecked())));
        onData(withKey("drunk_mode_settings_time")).check(matches(not(isEnabled())));
    }

    //Makes sure that state corresponds to the toggle's state
    @Test
    public void testToggleWhenChecked(){
        boolean checked = activityTestRule.getActivity().isDrunkChecked();
        SystemClock.sleep(MILLIS_DELAY);
        if(checked){
            onData(withKey("drunk_mode_settings_time")).check(matches(isEnabled()));
            onData(withKey("drunk_mode_settings_toggle")).perform(click());
        }
        onData(withKey("drunk_mode_settings_toggle")).check(matches(not(isChecked())));
        onData(withKey("drunk_mode_settings_time")).check(matches(not(isEnabled())));
    }
}
