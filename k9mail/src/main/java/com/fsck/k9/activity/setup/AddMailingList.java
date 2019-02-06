package com.fsck.k9.activity.setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.fsck.k9.R;


public class AddMailingList extends AppCompatActivity {

    Button button_add;
    Button cancel;

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
                //add db method to insert mailing list
                
                Intent intent = new Intent(getApplicationContext(), MailingListMenu.class);
                startActivity(intent);
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
