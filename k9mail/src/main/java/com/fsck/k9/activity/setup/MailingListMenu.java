package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
    private List<String> mailingListNames = new ArrayList<>();
    private DaoSession daoSession;
    Button add_mailing_list;

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
                savedInstanceState.getBoolean("refresh needed")){
            Bundle noUpdate = new Bundle();
            noUpdate.putBoolean("refresh needed", false);
            getIntent().replaceExtras(noUpdate);
            recreate();
        }
        if(savedInstanceState != null &&
                savedInstanceState.containsKey("under test") &&
                    savedInstanceState.getBoolean("under test")){
            mailingLists = new ArrayList<>();
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

        ListView list = (ListView)findViewById(android.R.id.list);
        registerForContextMenu(list);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, MailingListEmailListMenu.class);
        intent.putExtra("mailingListId", mailingLists.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.mailing_list_floating_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.rename:{
                Intent intent = new Intent(this, RenameMailingList.class);
                intent.putExtra("mailingListId", mailingLists.get(info.position).getId());
                startActivity(intent);
                break;
            }

            case R.id.delete:{
                Intent intent = new Intent(this, RemoveMailingList.class);
                intent.putExtra("mailingListId", mailingLists.get(info.position).getId());
                startActivity(intent);
                break;
            }

        }
        return super.onContextItemSelected(item);
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
