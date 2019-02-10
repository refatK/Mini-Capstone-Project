package com.fsck.k9.activity.setup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.DaoSession;

public class AddEmailToMailingList extends AppCompatActivity {

    private DaoSession daoSession;
    private Button addButton;
    private Button cancelButton;
    private EditText emailAddressInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailing_list_add_email);

    }
}