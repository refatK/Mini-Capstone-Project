package com.fsck.k9.activity.compose;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;

public class SaveScheduledMessageTask extends AsyncTask<Void, Void, Void> {
    Context context;
    Account account;
    Contacts contacts;
    Handler handler;
    Message message;
    long scheduledId;
    boolean saveRemotely;

    public SaveScheduledMessageTask(Context context, Account account, Contacts contacts,
                           Handler handler, Message message, long scheduledId, boolean saveRemotely) {
        this.context = context;
        this.account = account;
        this.contacts = contacts;
        this.handler = handler;
        this.message = message;
        this.scheduledId = scheduledId;
        this.saveRemotely = saveRemotely;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final MessagingController messagingController = MessagingController.getInstance(context);
        Message scheduledMessage = messagingController.saveScheduled(account, message, scheduledId, saveRemotely);
        scheduledId = messagingController.getId(scheduledMessage);

        android.os.Message msg = android.os.Message.obtain(handler, MessageCompose.MSG_SAVED_SCHEDULED, scheduledId);
        handler.sendMessage(msg);
        return null;
    }
}
