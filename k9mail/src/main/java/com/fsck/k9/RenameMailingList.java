package com.fsck.k9;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.activity.setup.MailingListMenu;

public class RenameMailingList extends Activity {

    private DaoSession daoSession;
    Button button_add;
    Button cancel;
    EditText mailingListNameInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mailing_list);

        //add button
        button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mailingListNameInput = findViewById(R.id.mailingListName);

                //Adds new list to the DB if the name is not empty or null
                if(mailingListNameInput != null &&
                        !mailingListNameInput.getText().toString().equals("")) {
                    daoSession = ((K9) getApplication()).getDaoSession();
                    MailingList newMailingList = new MailingList();
                    newMailingList.setName(mailingListNameInput.getText().toString());
                    daoSession.getMailingListDao().insert(newMailingList);
                    daoSession.clear();
                    Intent i = new Intent(getApplicationContext(), MailingListMenu.class);
                    i.putExtra("refresh needed", true);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(i);


                }
                finish();
            }

        });

        //cancel button
        cancel = (Button) findViewById(R.id.button_cancel);
        cancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // do nothing and show mailing list menu
                finish();
            }

        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.mailing_list_floating_context_menu, menu);
    }
}
