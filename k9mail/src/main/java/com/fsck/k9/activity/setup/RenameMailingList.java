package com.fsck.k9.activity.setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.DaoSession;
import com.fsck.k9.K9;
import com.fsck.k9.MailingList;
import com.fsck.k9.R;


public class RenameMailingList extends AppCompatActivity {

    private DaoSession daoSession;
    Button button_rename;
    Button cancel;
    EditText mailingListNameInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_mailing_list);

        Intent intent = getIntent();
        final Long mailingListID = intent.getLongExtra("mailingListId", -1);

        //rename button
        button_rename = (Button) findViewById(R.id.button_rename);
        button_rename.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mailingListNameInput = findViewById(R.id.mailingListName);

                //Adds new list to the DB if the name is not empty or null
                if(mailingListNameInput != null &&
                        !mailingListNameInput.getText().toString().equals("")) {
                    daoSession = ((K9) getApplication()).getDaoSession();
                    MailingList toRename = daoSession.getMailingListDao().loadByRowId(mailingListID);
                    toRename.setName(mailingListNameInput.getText().toString());
                    daoSession.getMailingListDao().update(toRename);
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


}
