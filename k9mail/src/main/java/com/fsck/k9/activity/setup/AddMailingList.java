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


public class AddMailingList extends AppCompatActivity {

    private DaoSession daoSession;
    Button button_add;
    Button cancel;
    EditText mailingListNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
