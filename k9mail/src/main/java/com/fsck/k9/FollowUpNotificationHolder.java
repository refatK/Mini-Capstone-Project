package com.fsck.k9;

import com.fsck.k9.mail.Message;

public class FollowUpNotificationHolder {
    private String recipientAddresses;
    private String dateTime;

    public static FollowUpNotificationHolder makeFNHolder(Message message) {
        return null;
    }

    public String getRecipientAddresses() {
        return recipientAddresses;
    }

    public String getDateTime() {
        return dateTime;
    }
}
