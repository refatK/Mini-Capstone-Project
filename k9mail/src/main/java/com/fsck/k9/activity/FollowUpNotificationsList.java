package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fsck.k9.Account;
import com.fsck.k9.FollowUpNotificationHolder;
import com.fsck.k9.FollowUpNotificationsListAdapter;
import com.fsck.k9.FollowUpReminderEmail;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.Preferences;
import com.fsck.k9.R;
import com.fsck.k9.activity.setup.AddMailingList;
import com.fsck.k9.activity.setup.EditQuickReply;
import com.fsck.k9.activity.setup.RemoveQuickReply;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.service.ActivateDrunkMode;
import static com.fsck.k9.FollowUpNotificationHolder.makeFNHolder;

import java.util.Calendar;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.fsck.k9.K9.daoSession;

public class FollowUpNotificationsList extends K9ListActivity {

    public static final int EDIT_TIME = 1;

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


        ArrayAdapter<FollowUpNotificationHolder> fNListAdapter = new FollowUpNotificationsListAdapter(
                this, R.layout.follow_up_notification_list_item,  followupHolders);
        setListAdapter(fNListAdapter);
        registerForContextMenu(getListView());
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.follow_up_notifications_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.reschedule:{
                Intent intent = new Intent(this, SetFollowUpReminderDateAndTime.class);
                intent.putExtra("fNId", followups.get(info.position).getId());
                intent.putExtra("currentDate", new Date(followups.get(info.position).getReminderDateTime()));
                startActivityForResult(intent,EDIT_TIME);
                break;
            }

            case R.id.delete:{

            }

        }
        return super.onContextItemSelected(item);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == EDIT_TIME ) {
            Long fNId = getIntent().getLongExtra("fNId", 0L);
            Long newDateInMillis = getIntent().getLongExtra("newFollowUpReminderDate", 0L);
            setFollowUpReminderDateAndTime(fNId, newDateInMillis);

        }
    }
    private void setFollowUpReminderDateAndTime(Long fNid, Long DateInMillis) {

        Calendar followUpReminderDate = Calendar.getInstance();
        followUpReminderDate.setTimeInMillis(DateInMillis);

        Toast.makeText(getApplicationContext(), "Follow-Up Reminder will alert you at: "
                        + (followUpReminderDate.get(Calendar.MONTH) + 1) + "/"
                        + followUpReminderDate.get(Calendar.DAY_OF_MONTH) + "/"
                        + followUpReminderDate.get(Calendar.YEAR) + " @ "
                        + followUpReminderDate.get(Calendar.HOUR_OF_DAY) + ":"
                        + ((followUpReminderDate.get(Calendar.MINUTE) < 10) ? "0" : "")
                        + (followUpReminderDate.get(Calendar.MINUTE)),
                Toast.LENGTH_LONG).show();

        FollowUpReminderEmail followUpReminderEmail = null;

        for(FollowUpReminderEmail followup: followups){
            if(followup.getEmailID() == fNid) {
                followUpReminderEmail = followup;
                followup.setReminderDateTime(followUpReminderDate.getTimeInMillis());
            }
        }
        daoSession.getFollowUpReminderEmailDao().insertOrReplace(followUpReminderEmail);
    }

}
