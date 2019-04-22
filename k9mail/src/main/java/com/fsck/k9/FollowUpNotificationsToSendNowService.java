package com.fsck.k9;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.fsck.k9.activity.MessageReference;
import com.fsck.k9.activity.compose.MessageActions;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.notification.NotificationIds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FollowUpNotificationsToSendNowService extends IntentService {

    private DaoSession daoSession;
    private List<FollowUpReminderEmail> allFollowUpReminders = new ArrayList();
    private List<FollowUpReminderEmail> remindersToSendNow = new ArrayList();

    public FollowUpNotificationsToSendNowService() {
        super("FollowUpNotificationsToSendNowService");
    }

    @Override
    protected void onHandleIntent(Intent i) {
        Log.d(null, "Hit FR start");
        daoSession = ((K9)getApplication()).getDaoSession();
        allFollowUpReminders = daoSession.getFollowUpReminderEmailDao().loadAll();

        for (FollowUpReminderEmail e : allFollowUpReminders) {
            long timeNow = Calendar.getInstance().getTimeInMillis();
            if (e.getReminderDateTime() <= timeNow) {
                remindersToSendNow.add(e);
            }
        }

        if (!remindersToSendNow.isEmpty()) {
            sendReminders(remindersToSendNow);
        }
        else
        {
            stopSelf();
        }
    }

    private String generatePushNotificationString(FollowUpReminderEmail email, Account account){

        Long emailID = email.getEmailID();
        Message message = null;
        String subject = "";
        String recipientAddresses = "";

        try {
            message = LocalStore.getInstance(account,
                    getApplicationContext()).getLocalMessageByMessageId(emailID);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        subject = message.getSubject();

        Address[] recipients = message.getRecipients(Message.RecipientType.TO);
        for(Address a : recipients) {
            recipientAddresses += (a.getAddress()+", ");
        }
        recipientAddresses
                = recipientAddresses.substring(0, recipientAddresses.length()-2);

       return ("No reply has been received from "+ recipientAddresses + " about "
               + subject +" you might want to follow up with them");
    }

    private void sendReminders(List<FollowUpReminderEmail> remindersToSendNow) {
        Log.d(null, "Hit sendReminders");
        String notificationText;
        String accountID;
        Preferences prefs;
        Account account;
        NotificationCompat.Builder builder;
        NotificationManagerCompat notificationManager;
        int notificationId;

        //Loop to send each reminder in the remindersToSendNow list
        for (int i = 0; i < remindersToSendNow.size(); i++) {
            Log.d(null, "In sendReminders loop");

            accountID = remindersToSendNow.get(i).getAccountID();
            prefs = Preferences.getPreferences(getApplicationContext());
            account = prefs.getAccount(accountID);
            notificationManager = NotificationManagerCompat.from(this);

            notificationId = NotificationIds.getFollowUpReminderNotificationId(account);
            notificationText = generatePushNotificationString(remindersToSendNow.get(i), account);

            PendingIntent pendingIntent = getIntentToFollowUp(remindersToSendNow.get(i), account, notificationId);

            //builder is used to forge to grab the parts of a Notification object
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notify_check_mail)
                    .setContentTitle("Follow Up Reminder")
                    .setContentText(notificationText)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(notificationText))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            //builder.build() generates the Notification object itself, .notify displays it
            notificationManager.notify(notificationId, builder.build());

            //Delete processed FollowUpReminderEmailDao entry
            daoSession.getFollowUpReminderEmailDao().delete(remindersToSendNow.get(i));
        }

        remindersToSendNow.clear();
    }

    private PendingIntent getIntentToFollowUp(FollowUpReminderEmail email, Account account, int notificationId) {

        LocalMessage message = null;

        try {
            message = account.getLocalStore().getLocalMessageByMessageId(email.getEmailID());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MessageReference ref = message.makeMessageReference();
        Intent intent = MessageActions.getActionForwardIntent(this, ref, null, true);

        return PendingIntent.getActivity(this, notificationId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
    }
}
