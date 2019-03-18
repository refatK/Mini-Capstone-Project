package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fsck.k9.DaoSession;
import com.fsck.k9.K9;
import com.fsck.k9.QuickReply;
import com.fsck.k9.R;

public class RemoveQuickReply extends AppCompatActivity {
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_reply_remove);

        Intent intent = getIntent();
        final Long quickReplyID = intent.getLongExtra("quickReplyId", -1);

        daoSession = ((K9) getApplication()).getDaoSession();
        QuickReply quickReplyToDelete = daoSession.getQuickReplyDao().loadByRowId(quickReplyID);
        daoSession.getQuickReplyDao().delete(quickReplyToDelete);
        daoSession.clear();

        Intent i = new Intent(getApplicationContext(), QuickRepliesMenu.class);
        i.putExtra("refresh needed", true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);

        finish();
    }
}
