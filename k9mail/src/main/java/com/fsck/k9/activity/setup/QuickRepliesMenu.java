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
import com.fsck.k9.QuickReply;
import com.fsck.k9.R;
import com.fsck.k9.ScheduledEmail;
import com.fsck.k9.activity.K9ListActivity;

import java.util.ArrayList;
import java.util.List;

public class QuickRepliesMenu extends K9ListActivity {

    private List<QuickReply> quickReplies;
    private List<String> quickReplyBodies = new ArrayList<>();
    private DaoSession daoSession;
    Button add_quick_reply;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null &&
                savedInstanceState.getBoolean("refresh needed", false)){
            Bundle noUpdate = new Bundle();
            noUpdate.putBoolean("refresh needed", false);
            getIntent().replaceExtras(noUpdate);
            recreate();
        }

        else{
            daoSession = ((K9)getApplication()).getDaoSession();
            quickReplies = daoSession.getQuickReplyDao().loadAll();
        }
        setContentView(R.layout.activity_quick_replies_menu);

        for (QuickReply qR : quickReplies ) {
            quickReplyBodies.add(qR.getBody());
        }

        ArrayAdapter<String> quickReplyAdapter = new ArrayAdapter<String>(
                this, R.layout.quick_reply_menu_item,  quickReplyBodies);
        setListAdapter(quickReplyAdapter);

        //action when pressing add new mailing list button
        add_quick_reply = (Button) findViewById(R.id.add_quick_reply);
        add_quick_reply.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //TODO: Add the functionality for adding QRs
            }

        });
    }
}

