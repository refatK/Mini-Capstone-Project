package com.fsck.k9.activity.setup;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fsck.k9.DaoSession;
import com.fsck.k9.K9;
import com.fsck.k9.QuickReply;
import com.fsck.k9.R;
import com.fsck.k9.SendQuickReplyService;
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
        setTitle("Quick Replies");
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
                Intent intent = new Intent(getApplicationContext(), AddQuickReply.class);
                startActivity(intent);
            }
        });

        if(getIntent().getBooleanExtra("Sending",false)) {
            add_quick_reply.setText("Select A Quick Reply To Send");
            add_quick_reply.setClickable(false);
            add_quick_reply.setWidth(1080);
            add_quick_reply.setBackgroundColor(Color.DKGRAY);
            add_quick_reply.setTextColor(Color.LTGRAY);
        }
        else{
            registerForContextMenu(getListView());
        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.quick_replies_floating_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.edit:{
                Intent intent = new Intent(this, EditQuickReply.class);
                intent.putExtra("quickReplyId", quickReplies.get(info.position).getId());
                startActivity(intent);
                break;
            }

            case R.id.delete:{
                Intent intent = new Intent(this, RemoveQuickReply.class);
                intent.putExtra("quickReplyId", quickReplies.get(info.position).getId());
                startActivity(intent);
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = getIntent();
        if(i.getBooleanExtra("Sending", false)){
            final String messageId = i.getStringExtra("messageIdentityString");
            Toast.makeText(this, "Sending QR: "+quickReplyBodies.get(position), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, SendQuickReplyService.class);
            intent.putExtra("messageIdentityString", messageId);
            intent.putExtra( "quickReply", quickReplyBodies.get(position));
            setResult(RESULT_OK, intent);
            super.finish();
        }


    }


}

