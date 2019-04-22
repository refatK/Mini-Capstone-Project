package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.fsck.k9.Account;
import com.fsck.k9.FollowUpNotificationHolder;
import com.fsck.k9.FollowUpNotificationsListAdapter;
import com.fsck.k9.FollowUpReminderEmail;
import com.fsck.k9.Preferences;
import com.fsck.k9.R;
import com.fsck.k9.activity.setup.RemoveFollowUpNotification;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mailstore.LocalStore;
import com.fsck.k9.service.ActivateDrunkMode;
import static com.fsck.k9.FollowUpNotificationHolder.makeFNHolder;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static com.fsck.k9.K9.daoSession;

public class FollowUpNotificationsList extends K9ListActivity {

    public static final int EDIT_TIME = 1;

    private List<FollowUpReminderEmail> followups;
    private List<FollowUpNotificationHolder> followupHolders
            = new ArrayList<>();

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

        //Designed for testing remove function when list is empty
        if(getIntent().getBooleanExtra("test remove", false) &&
                followups.isEmpty()){

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy @ hh:mm a",
                    Locale.CANADA);

            FollowUpNotificationHolder fNH = new FollowUpNotificationHolder(
                    "Doctor Styles", null, "We need help");
            FollowUpReminderEmail fN = new FollowUpReminderEmail(null, null,
                    null, Calendar.getInstance().getTimeInMillis());
            fNH.setDateTime(sdf.format(new Date(fN.getReminderDateTime())));

            //Update the followups
            daoSession.getFollowUpReminderEmailDao().insert(fN);
            followupHolders.add(fNH);

            getIntent().putExtra("test remove", false);
        }else if(getIntent().getBooleanExtra("test remove", false)
                && followups.size() == 1){
            //We don't want it to add something to the list after it's empty
            getIntent().putExtra("test remove", false);
        }

        if(!followups.isEmpty()){
            for(FollowUpReminderEmail fN : followups) {
                followupHolders.add(retrieveHolder(fN));
            }
        }else{
            //Refreshes the list if it's empty just in case it's a test
            followups = daoSession.getFollowUpReminderEmailDao().loadAll();
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
                Intent intent = new Intent(this, RemoveFollowUpNotification.class);
                intent.putExtra("fNId", followups.get(info.position).getId());
                startActivity(intent);
                break;
            }

        }
        return super.onContextItemSelected(item);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == EDIT_TIME ) {
            Long fNId = data.getLongExtra("fNId", 1L);
            Long newDateInMillis = data.getLongExtra("newFollowUpReminderDate", 1L);
            setFollowUpReminderDateAndTime(fNId, newDateInMillis);

        }
    }
    private void setFollowUpReminderDateAndTime(Long fNid, Long DateInMillis) {

        Calendar followUpReminderDate = Calendar.getInstance();
        followUpReminderDate.setTimeInMillis(DateInMillis);

        FollowUpReminderEmail followup = daoSession.getFollowUpReminderEmailDao().load(fNid);
        followup.setReminderDateTime(followUpReminderDate.getTimeInMillis());
        daoSession.getFollowUpReminderEmailDao().update(followup);
        recreate();

    }

}
