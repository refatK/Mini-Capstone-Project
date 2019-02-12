package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.fsck.k9.DaoSession;
import com.fsck.k9.EmailAddress;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.R;

public class RemoveEmailAddress extends Activity {

    private DaoSession daoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_email_address);

        Intent intent = getIntent();
        final Long emailAddressID = intent.getLongExtra("id", -1);
        getIntent().removeExtra("id");
        daoSession = ((K9) getApplication()).getDaoSession();
        EmailAddress email = daoSession.getEmailAddressDao().load(emailAddressID);
        daoSession.delete(email);
        daoSession.clear();
        Intent i = new Intent(getApplicationContext(), MailingListEmailListMenu.class);
        i.putExtra("refresh needed", true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }

}
