package com.fsck.k9;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.fsck.k9.mail.Message;
import com.fsck.k9.mailstore.LocalStore;

import java.util.ArrayList;


public class EmailsToSendNow extends IntentService {

    private LocalStore db;
    Account account;

    public EmailsToSendNow() {
        super("EmailsToSendNow");
    }

    @Override
    protected void onHandleIntent(Intent i) {

    }
}
