package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.fsck.k9.DaoSession;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.R;

public class RemoveMailingList extends AppCompatActivity {

    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_mailing_list);

        Intent intent = getIntent();
        final Long mailingListID = intent.getLongExtra("mailingListId", -1);

        daoSession = ((K9) getApplication()).getDaoSession();
        MailingList toDelete = daoSession.getMailingListDao().loadByRowId(mailingListID);
        //toDelete.setName(mailingListNameInput.getText().toString());
        daoSession.getMailingListDao().delete(toDelete);
        daoSession.clear();
        Intent i = new Intent(getApplicationContext(), MailingListMenu.class);
        i.putExtra("refresh needed", true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
        finish();
    }

}
