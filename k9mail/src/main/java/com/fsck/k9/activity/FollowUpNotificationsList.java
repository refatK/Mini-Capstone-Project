package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.fsck.k9.Account;
import com.fsck.k9.FollowUpNotificationHolder;
import com.fsck.k9.FollowUpNotificationsListAdapter;
import com.fsck.k9.FollowUpReminderEmail;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.Preferences;
import com.fsck.k9.R;
import com.fsck.k9.activity.setup.AddMailingList;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.service.ActivateDrunkMode;
import static com.fsck.k9.FollowUpNotificationHolder.makeFNHolder;

import java.util.ArrayList;
import java.util.List;

import static com.fsck.k9.K9.daoSession;

public class FollowUpNotificationsList extends K9ListActivity {

    private List<FollowUpReminderEmail> followups;
    private List<FollowUpNotificationHolder> followupHolders
            = new ArrayList<FollowUpNotificationHolder>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_notifications_list);

        Intent intent = new Intent(this, ActivateDrunkMode.class);
        startService(intent);

        if(savedInstanceState != null &&
                savedInstanceState.getBoolean("refresh needed", false)){
            Bundle noUpdate = new Bundle();
            noUpdate.putBoolean("refresh needed", false);
            getIntent().replaceExtras(noUpdate);
            recreate();
        }

        followups = daoSession.getFollowUpReminderEmailDao().loadAll();

        for(FollowUpReminderEmail fN : followups) {
            followupHolders.add(retrieveHolder(fN));
        }

        setContentView(R.layout.activity_follow_up_notifications_list);


        ArrayAdapter<FollowUpNotificationHolder> mailingListAdapter = new FollowUpNotificationsListAdapter(
                this, R.layout.follow_up_notification_list_item,  followupHolders);
        setListAdapter(mailingListAdapter);
    }
    public FollowUpNotificationHolder retrieveHolder(FollowUpReminderEmail fN){
        String accountID = fN.getAccountID();
        Preferences prefs = Preferences.getPreferences(getApplicationContext());
        Account account = prefs.getAccount(accountID);
        //Generate the message
        long emailID = fN.getEmailID();
        Message message = null;
        try {
            message = LocalStore.getInstance(account,
                    getApplicationContext()).getLocalMessageByMessageId(emailID);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new FollowUpNotificationHolder();
        }
        return makeFNHolder(message, fN);

    }

}
