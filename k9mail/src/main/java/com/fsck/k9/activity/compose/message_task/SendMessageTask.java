package com.fsck.k9.activity.compose.message_task;

import android.content.Context;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.activity.MessageReference;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mailstore.LocalMessage;

import timber.log.Timber;

public class SendMessageTask extends MessageTask {


    private MessageReference messageReference;
    private long localMessageId;

    public SendMessageTask(Context context, Account account, Contacts contacts,
                    Handler handler, Message message, Long draftId, MessageReference messageReference) {

        super(context, account, contacts, handler, message, draftId);
        this.messageReference = messageReference;
    }

    @Override
    protected void handleMessageTask() {
        try {
            contacts.markAsContacted(message.getRecipients(Message.RecipientType.TO));
            contacts.markAsContacted(message.getRecipients(Message.RecipientType.CC));
            contacts.markAsContacted(message.getRecipients(Message.RecipientType.BCC));
            updateReferencedMessage();
        } catch (Exception e) {
            Timber.e(e, "Failed to mark contact as contacted.");
        }

        LocalMessage messageSent = MessagingController.getInstance(context).sendMessage(account, message, null);
        localMessageId = messageSent.getDatabaseId();

        if (draftId != null) {
            MessagingController.getInstance(context).deleteDraft(account, draftId);
        }

    }

    @Override
    protected void returnNotificationToMessageHandler() {
        android.os.Message msg = android.os.Message.obtain(handler, MessageCompose.MSG_SENT, localMessageId);
        handler.sendMessage(msg);
    }

    /**
     * Set the flag on the referenced message(indicated we replied / forwarded the message)
     **/
    private void updateReferencedMessage() {
        if (messageReference != null && messageReference.getFlag() != null) {
            Timber.d("Setting referenced message (%s, %s) flag to %s",
                    messageReference.getFolderName(),
                    messageReference.getUid(),
                    messageReference.getFlag());

            final Account account = Preferences.getPreferences(context)
                    .getAccount(messageReference.getAccountUuid());
            final String folderName = messageReference.getFolderName();
            final String sourceMessageUid = messageReference.getUid();
            MessagingController.getInstance(context).setFlag(account, folderName,
                    sourceMessageUid, messageReference.getFlag(), true);
        }
    }
}
