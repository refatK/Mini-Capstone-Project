package com.fsck.k9.activity.compose;

import android.content.Context;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;

public class SaveScheduledMessageTask extends SaveMessageTask {

    public SaveScheduledMessageTask(Context context, Account account, Contacts contacts,
                                Handler handler, Message message, long draftId, boolean saveRemotely) {
        super(context, account, contacts, handler, message, draftId, saveRemotely);
    }

    @Override
    protected void saveMessage() {
        final MessagingController messagingController = MessagingController.getInstance(context);
        Message scheduledMessage = messagingController.saveDraft(account, message, draftId, saveRemotely, true);
        draftId = messagingController.getId(scheduledMessage);
    }

    @Override
    protected void returnNotificationToMessageHandler() {
        android.os.Message msg = android.os.Message.obtain(handler, MessageCompose.MSG_SEND_LATER, draftId);
        handler.sendMessage(msg);
    }

}
