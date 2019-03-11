package com.fsck.k9;

import android.app.IntentService;
import android.content.Intent;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mailstore.LocalStore;

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
            sendEmails(emailsToSendNow);
        }
        else
        {
            stopSelf();
        }
    }

    private void sendEmails(List<ScheduledEmail> emailsToSendNow) {
        Account account;
        Preferences prefs;
        String accountID;
        Long emailID;
        Message message;

        //Loop to send each email in the emailsToSendNow list
        for (int i = 0; i < emailsToSendNow.size(); i++) {
            //Generating the account
            accountID = emailsToSendNow.get(i).getAccountID();
            prefs = Preferences.getPreferences(getApplicationContext());
            account = prefs.getAccount(accountID);

            //Generate the message
            emailID = emailsToSendNow.get(i).getEmailID();
            message = LocalStore.getLocalMessageByMessageId(emailID);

            //Send the email now that the parameters are created
            MessagingController.getInstance(getApplicationContext()).sendMessage(account, message, null);
        }

        //Clear sent emails
        emailsToSendNow.clear();
        daoSession.getScheduledEmailsDao().delete(ScheduledEmail);
    }
}
