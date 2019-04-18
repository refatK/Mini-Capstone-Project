package com.fsck.k9.activity.compose.message_task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;

public abstract class MessageTask extends AsyncTask<Void, Void, Void> {

    Context context;
    Account account;
    Contacts contacts;
    Handler handler;
    Message message;
    Long draftId;

    public MessageTask(Context context, Account account, Contacts contacts,
                       Handler handler, Message message, Long draftId) {
        this.context = context;
        this.account = account;
        this.contacts = contacts;
        this.handler = handler;
        this.message = message;
        this.draftId = draftId;
    }

    protected abstract void handleMessageTask();
    protected abstract void returnNotificationToMessageHandler();

    @Override
    final protected Void doInBackground(Void... params) {
        handleMessageTask();
        returnNotificationToMessageHandler();
        return null;
    }
}
