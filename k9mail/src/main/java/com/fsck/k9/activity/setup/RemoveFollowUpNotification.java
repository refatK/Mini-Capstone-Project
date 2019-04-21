package com.fsck.k9.activity.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fsck.k9.DaoSession;
import com.fsck.k9.FollowUpReminderEmail;
import com.fsck.k9.K9;
import com.fsck.k9.R;
import com.fsck.k9.activity.FollowUpNotificationsList;

public class RemoveFollowUpNotification extends Activity {

    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_follow_up_notification);

        Intent intent = getIntent();
        final Long quickReplyID = intent.getLongExtra("fNId", -1);

        daoSession = ((K9) getApplication()).getDaoSession();
        FollowUpReminderEmail followUpReminderEmail = daoSession.getFollowUpReminderEmailDao().loadByRowId(quickReplyID);
        daoSession.getFollowUpReminderEmailDao().delete(followUpReminderEmail);
        daoSession.clear();

        Intent i = new Intent(getApplicationContext(), FollowUpNotificationsList.class);
        i.putExtra("refresh needed", true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);

        finish();
    }
}
