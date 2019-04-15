package com.fsck.k9.activity;

import android.os.Bundle;
import android.app.Activity;

import com.fsck.k9.R;

public class FollowUpNotificationsList extends K9ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_notifications_list);
    }

}
