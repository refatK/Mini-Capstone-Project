package com.fsck.k9.activity.compose.message_task;

import android.content.Context;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;

public class SaveScheduledMessageTask extends MessageTask {

    private boolean saveRemotely;
    private long scheduledId;

    public SaveScheduledMessageTask(Context context, Account account, Contacts contacts,
                                Handler handler, Message message, Long draftId, boolean saveRemotely,
                                    long scheduledId) {

        super(context, account, contacts, handler, message, draftId);
        this.saveRemotely = saveRemotely;
        this.scheduledId = scheduledId;
    }

    @Override
    protected void handleMessageTask() {
        final MessagingController messagingController = MessagingController.getInstance(context);
        Message scheduledMessage = messagingController.saveDraft(account, message, scheduledId, saveRemotely, true);
        scheduledId = messagingController.getId(scheduledMessage);

        if (draftId != null) {
            MessagingController.getInstance(context).deleteDraft(account, draftId);
        }
    }

    @Override
    protected void returnNotificationToMessageHandler() {
        android.os.Message msg = android.os.Message.obtain(handler, MessageCompose.MSG_SAVED_SCHEDULED, scheduledId);
        handler.sendMessage(msg);
    }

}
