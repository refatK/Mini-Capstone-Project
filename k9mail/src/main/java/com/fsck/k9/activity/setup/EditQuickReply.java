package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.DaoSession;
import com.fsck.k9.K9;
import com.fsck.k9.QuickReply;
import com.fsck.k9.R;


public class EditQuickReply extends AppCompatActivity {

    private DaoSession daoSession;
    Button button_rename;
    Button cancel;
    EditText quickReplyInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_reply_edit);


        Intent intent = getIntent();
        final Long quickReplyID = intent.getLongExtra("quickReplyId", -1);

        //rename button
        button_rename = (Button) findViewById(R.id.button_rename);
        button_rename.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                quickReplyInput = findViewById(R.id.quickReply);

                //Adds new list to the DB if the name is not empty or null
                if(quickReplyInput != null &&
                        !quickReplyInput.getText().toString().equals("")) {
                    daoSession = ((K9) getApplication()).getDaoSession();
                    QuickReply toRename = daoSession.getQuickReplyDao().loadByRowId(quickReplyID);
                    toRename.setBody(quickReplyInput.getText().toString());
                    daoSession.getQuickReplyDao().update(toRename);
                    daoSession.clear();
                    Intent i = new Intent(getApplicationContext(), QuickRepliesMenu.class);
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
                // do nothing and show quick reply menu
                finish();
            }

        });
    }


}
