package com.fsck.k9.activity.compose;

import android.content.Context;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;

public class SaveDraftMessageTask extends SaveMessageTask {

    public SaveDraftMessageTask(Context context, Account account, Contacts contacts,
                                Handler handler, Message message, long draftId, boolean saveRemotely) {
        super(context, account, contacts, handler, message, draftId, saveRemotely);
    }

    @Override
    protected void saveMessage() {
        final MessagingController messagingController = MessagingController.getInstance(context);
        Message draftMessage = messagingController.saveDraft(account, message, draftId, saveRemotely, false);
        draftId = messagingController.getId(draftMessage);
    }

    @Override
    protected void returnNotificationToMessageHandler() {
        android.os.Message msg = android.os.Message.obtain(handler, MessageCompose.MSG_SAVED_DRAFT, draftId);
        handler.sendMessage(msg);
    }

}
