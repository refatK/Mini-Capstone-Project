package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fsck.k9.DaoSession;
import com.fsck.k9.EmailAddress;
import com.fsck.k9.EmailAddressValidator;
import com.fsck.k9.K9;
import com.fsck.k9.R;

public class AddEmailToMailingList extends AppCompatActivity {

    private DaoSession daoSession;
    private Button addButton;
    private Button cancelButton;
    private EditText emailAddressInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailing_list_add_email);

        Intent intent = getIntent();
        final Long mailingListID = intent.getLongExtra("mailingListId", -1);

        addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddressInput = findViewById(R.id.emailAddress);

                EmailAddressValidator validator = new EmailAddressValidator();

                if(emailAddressInput != null && !emailAddressInput.getText().toString().equals("")
                        && validator.isValidAddressOnly(emailAddressInput.getText().toString().trim())) {
                    daoSession = ((K9)getApplication()).getDaoSession();
                    EmailAddress newEmailAddress = new EmailAddress();
                    newEmailAddress.setEmail(emailAddressInput.getText().toString());
                    newEmailAddress.setMailingListID(mailingListID);
                    daoSession.getEmailAddressDao().insert(newEmailAddress);
                    daoSession.clear();
                    Intent i = new Intent(getApplicationContext(), MailingListEmailListMenu.class);
                    i.putExtra("refresh needed", true);
                    i.putExtra("mailingListId", mailingListID);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Email Address. Please add another one.", Toast.LENGTH_SHORT).show();
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