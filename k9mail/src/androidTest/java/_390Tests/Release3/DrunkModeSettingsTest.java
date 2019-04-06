package _390Tests.Release3;

import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.setup.DrunkModeSettings;

import org.junit.Rule;

public class DrunkModeSettingsTest {

    @Rule
    public ActivityTestRule<DrunkModeSettings> mActivityTestRule = new ActivityTestRule<>(DrunkModeSettings.class);
}
