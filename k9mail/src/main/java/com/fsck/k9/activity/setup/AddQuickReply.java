package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fsck.k9.DaoSession;
import com.fsck.k9.K9;
import com.fsck.k9.QuickReply;
import com.fsck.k9.R;

public class AddQuickReply extends AppCompatActivity {
    private DaoSession daoSession;
    private Button addButton;
    private Button cancelButton;
    private EditText quickReplyInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_reply_add);

        addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickReplyInput = findViewById(R.id.quickReply);

                if(quickReplyInput != null && !quickReplyInput.getText().toString().equals("")) {
                    daoSession = ((K9)getApplication()).getDaoSession();
                    QuickReply newQuickReply = new QuickReply();
                    newQuickReply.setBody(quickReplyInput.getText().toString());
                    daoSession.getQuickReplyDao().insert(newQuickReply);
                    daoSession.clear();

                    Intent i = new Intent(getApplicationContext(), QuickRepliesMenu.class);
                    i.putExtra("refresh needed", true);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Quick Reply input. Please add another one.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

        });

        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
