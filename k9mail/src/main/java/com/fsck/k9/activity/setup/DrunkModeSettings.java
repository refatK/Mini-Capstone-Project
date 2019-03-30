package com.fsck.k9.activity.setup;

import android.os.Bundle;

import com.fsck.k9.activity.K9PreferenceActivity;

public class DrunkModeSettings extends K9PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.drunk_mode_settings_preferences);
    }
}
