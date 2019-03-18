package com.fsck.k9.activity.setup;

import android.os.Bundle;

import com.fsck.k9.R;
import com.fsck.k9.activity.K9ListActivity;

public class QuickRepliesMenu extends K9ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_replies_menu);
    }
}
