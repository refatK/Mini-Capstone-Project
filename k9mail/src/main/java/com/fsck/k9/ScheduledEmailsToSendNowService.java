package com.fsck.k9;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduledEmailsToSendNowService extends IntentService {

    private DaoSession daoSession;
    private List<ScheduledEmail> allScheduledEmails = new ArrayList();
    private List<ScheduledEmail> emailsToSendNow = new ArrayList();

    public ScheduledEmailsToSendNowService() {
        super("ScheduledEmailsToSendNowService");
    }

    @Override
    protected void onHandleIntent(Intent i) {
        daoSession = ((K9)getApplication()).getDaoSession();
        allScheduledEmails = daoSession.getScheduledEmailDao().loadAll();

        for (ScheduledEmail e : allScheduledEmails) {
            long timeNow = Calendar.getInstance().getTimeInMillis();
            if (e.getScheduledDateTime() < timeNow) {
                emailsToSendNow.add(e);
            }
        }

        if (!emailsToSendNow.isEmpty()) {
            sendEmails();
        }
    }

    private void sendEmails() {
        //TODO: Add method behaviour to send emails, use the MessageController class, as well as the others classes
        //TODO: Remember to delete the sent emails from the DAO after sending
        //TODO: use daoSession.getScheduledEmailsDao().delete(ScheduledEmail) to delete after sending
    }
}
