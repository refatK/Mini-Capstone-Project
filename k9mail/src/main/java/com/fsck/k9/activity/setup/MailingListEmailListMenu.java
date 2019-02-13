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
import com.fsck.k9.activity.K9ListActivity;
import com.fsck.k9.R;

import java.util.ArrayList;
import java.util.List;


public class MailingListEmailListMenu extends K9ListActivity {

    private DaoSession daoSession;
    private List<EmailAddress> emailAddresses;
    private List<String> emailAddressNames = new ArrayList<>();
    private Button addEmailButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailing_list_email_list_menu);


        if(getIntent().getBooleanExtra("refresh needed", false)){
            getIntent().removeExtra("refresh needed");
            getIntent().putExtra("refresh needed", false);
            recreate();
        }

        Intent intent = getIntent();
        final Long mailingListID = intent.getLongExtra("mailingListId", -1);

        if(savedInstanceState != null &&
                savedInstanceState.getBoolean("refresh needed") == true){
            recreate();
        }

        if(savedInstanceState != null && savedInstanceState.containsKey("testToggle")
                && savedInstanceState.getBoolean("testToggle") == true){
            emailAddresses = new ArrayList<>();
            emailAddresses.add(
                    new EmailAddress(null, null, "test1@test.com")
            );
            emailAddresses.add(
                    new EmailAddress(null, null, "test2@test.com")
            );
            emailAddresses.add(
                    new EmailAddress(null, null, "test3@test.com")
            );
        }else{
            daoSession = ((K9)getApplication()).getDaoSession();
            emailAddresses = daoSession.getEmailAddressDao()._queryMailingList_Emails(mailingListID);
        }

        for (EmailAddress eA : emailAddresses) {
            emailAddressNames.add(eA.getEmail());
        }

        addEmailButton = (Button) findViewById(R.id.mailing_list_add_email);
        addEmailButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentButton = new Intent(getApplicationContext(), AddEmailToMailingList.class);
                intentButton.putExtra("mailingListId", mailingListID);
                startActivity(intentButton);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.mailing_list_email_list_item, emailAddressNames
        );
        setListAdapter(adapter);

        ListView list = (ListView)findViewById(android.R.id.list);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.email_address_floating_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.delete:{
                Intent intent = new Intent(this, RemoveEmailAddress.class);
                intent.putExtra("id", emailAddresses.get(info.position).getId());
                intent.putExtra("mailingListId", getIntent().
                        getLongExtra("mailingListId", -1));
                startActivity(intent);
                break;
            }

        }
        return super.onContextItemSelected(item);
    }
}