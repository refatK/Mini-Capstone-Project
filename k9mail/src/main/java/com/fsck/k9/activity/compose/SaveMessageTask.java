package com.fsck.k9.activity.compose;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;

public abstract class SaveMessageTask extends AsyncTask<Void, Void, Void> {

    Context context;
    Account account;
    Contacts contacts;
    Handler handler;
    Message message;
    Long draftId;
    boolean saveRemotely;

    public SaveMessageTask(Context context, Account account, Contacts contacts,
                                Handler handler, Message message, Long draftId, boolean saveRemotely) {
        this.context = context;
        this.account = account;
        this.contacts = contacts;
        this.handler = handler;
        this.message = message;
        this.draftId = draftId;
        this.saveRemotely = saveRemotely;
    }

    protected abstract void saveMessage();

    protected abstract void returnNotificationToMessageHandler();

    @Override
    final protected Void doInBackground(Void... params) {
        saveMessage();
        returnNotificationToMessageHandler();
        return null;
    }
}
