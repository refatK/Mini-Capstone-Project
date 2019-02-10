package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import com.fsck.k9.DaoSession;
import com.fsck.k9.EmailAddress;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.R;
import com.fsck.k9.activity.K9ListActivity;

import java.util.ArrayList;
import java.util.List;

public class MailingListMenu extends K9ListActivity {

    private List<MailingList> mailingLists;
    private List<String> mailingListNames = new ArrayList<String>();
    private DaoSession daoSession;
    Button add_mailing_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null &&
                savedInstanceState.getBoolean("refresh needed") == true){
            recreate();
        }
        setContentView(R.layout.activity_mailing_list_menu);
        daoSession = ((K9)getApplication()).getDaoSession();
        mailingLists = daoSession.getMailingListDao().loadAll();
        for (MailingList mL : mailingLists) {
            mailingListNames.add(mL.getName());
        }
        //Test
        /*
        List<EmailAddress> mylist2 = daoSession.getMailingListDao().loadByRowId(1).getEmails();

        EmailAddress newEmail = new EmailAddress();
        newEmail.setEmail("a@321.com");
        newEmail.setMailingListID(daoSession.getMailingListDao().load(1L).getId());
        daoSession.insert(newEmail);
        mylist2.add(newEmail);
        */
        //Test
        ArrayAdapter<String> mailingListAdapter = new ArrayAdapter<String>(
                this, R.layout.mailing_list_menu_item,  mailingListNames);
        setListAdapter(mailingListAdapter);

        //action when pressing add new mailing list button
        add_mailing_list = (Button) findViewById(R.id.add_mailing_list);
        add_mailing_list.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AddMailingList.class);
                startActivity(intent);
            }

        });
        
    }

    //Test to make sure it gets the right emails, remove this later when you want to transition to other screens
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, MailingListEmailListMenu.class);
        intent.putExtra("MailingListId", mailingLists.get(position).getId());
        startActivity(intent);
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
