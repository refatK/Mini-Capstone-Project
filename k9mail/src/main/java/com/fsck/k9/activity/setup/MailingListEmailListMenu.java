package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.fsck.k9.DaoSession;
import com.fsck.k9.EmailAddress;
import com.fsck.k9.K9;
import com.fsck.k9.activity.K9ListActivity;
import com.fsck.k9.R;

import java.util.ArrayList;
import java.util.List;

public class MailingListEmailListMenu extends K9ListActivity{

    private DaoSession daoSession;
    private List<EmailAddress> emailAdresses;
    private List<String> emailAdressNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_address_list);

        Intent intent = getIntent();
        Long mailingListID = intent.getLongExtra("mailingListId", -1);

        if(mailingListID == -1){
            finish();
            return;
        }

        daoSession = ((K9)getApplication()).getDaoSession();
        emailAdresses = daoSession.getEmailAddressDao()._queryMailingList_Emails(mailingListID);

        //Test
        for (EmailAddress eA : emailAdresses) {
            emailAdressNames.add(eA.getEmail());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.email_address_list_item, emailAdresses);
}
