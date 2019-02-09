package com.fsck.k9.activity.setup;

import android.app.Application;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fsck.k9.DaoSession;
import com.fsck.k9.EmailAddress;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.R;
import com.fsck.k9.activity.EmailAddressList;
import com.fsck.k9.activity.K9ListActivity;

import java.util.ArrayList;
import java.util.List;

public class MailingListMenu extends K9ListActivity {

    private List<MailingList> mailingLists;
    private List<String> mailingListNames = new ArrayList<String>();
    private DaoSession daoSession;

    public List<MailingList> getMailingListsForTesting() {
        return mailingLists;
    }

    public List<String> getMailingListNamesForTesting() {
        return mailingListNames;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null &&
                savedInstanceState.getBoolean("refresh needed") == true){
            Bundle noUpdate = new Bundle();
            noUpdate.putBoolean("refresh needed", false);
            getIntent().replaceExtras(noUpdate);
            recreate();
        }
        if(savedInstanceState != null &&
                savedInstanceState.containsKey("under test") &&
                    savedInstanceState.getBoolean("under test") == true){
            mailingLists = new ArrayList<MailingList>();
            mailingLists.add(new MailingList(null, "TestList1"));
            mailingLists.add(new MailingList(null, "TestList2"));
            mailingLists.add(new MailingList(null, "TestList3"));
        }
        else{
            daoSession = ((K9)getApplication()).getDaoSession();
            mailingLists = daoSession.getMailingListDao().loadAll();
        }
        setContentView(R.layout.activity_mailing_list_menu);

        for (MailingList mL : mailingLists) {
            mailingListNames.add(mL.getName());
        }
        /*
        List<EmailAddress> mylist2 = daoSession.getMailingListDao().loadByRowId(1).getEmails();

        EmailAddress newEmail = new EmailAddress();
        newEmail.setEmail("a@321.com");
        newEmail.setMailingListID(daoSession.getMailingListDao().load(1L).getId());
        daoSession.insert(newEmail);
        mylist2.add(newEmail);
        */
        ArrayAdapter<String> mailingListAdapter = new ArrayAdapter<String>(
                this, R.layout.mailing_list_menu_item,  mailingListNames);
        setListAdapter(mailingListAdapter);
        
    }

    //Test to make sure it gets the right emails, remove this later when you want to transition to other screens
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getApplicationContext(),
                this.allEmailstoString(mailingLists.get(position)), Toast.LENGTH_SHORT ).show();
    }
    //this method is to get the string of comma seperated emails.
     private String allEmailstoString(MailingList mailingList){
        String allEmails = "";
        for(EmailAddress e : daoSession.getEmailAddressDao()._queryMailingList_Emails(mailingList.getId())){
            allEmails+=e.getEmail()+", ";
        }
        if(allEmails.equals(""))
            return "";
        return allEmails.substring(0, allEmails.length()-2);
    }
}
