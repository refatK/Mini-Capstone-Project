package com.fsck.k9;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.fsck.k9.activity.MessageReference;
import com.fsck.k9.controller.MessagingController;

public class SendQuickReplyService extends IntentService {

    private String messageReferenceString;
    private String quickReply;
    private MessageReference relatedMessageReference;
    private Account account;

    public SendQuickReplyService() {
        super("SendQuickReplyService");
    }

    @Override
    protected void onHandleIntent(Intent i) {
        messageReferenceString = i.getStringExtra("messageIdentityString");
        quickReply = i.getStringExtra("quickReply");
        relatedMessageReference = MessageReference.parse(messageReferenceString);

        final String accountUuid = relatedMessageReference.getAccountUuid();
        account = Preferences.getPreferences(this).getAccount(accountUuid);

        //MessagingController.getInstance(getApplicationContext()).sendMessage(account, createMessage(), null);
    }

    /*private Message createMessage()
    {

    }*/
}
