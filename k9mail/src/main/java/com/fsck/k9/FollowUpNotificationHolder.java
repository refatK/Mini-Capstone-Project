package com.fsck.k9;

import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FollowUpNotificationHolder {
    private String recipientAddresses;
    private String dateTime;

    public FollowUpNotificationHolder() {
        recipientAddresses = "";
        dateTime = "";
    }

    public static FollowUpNotificationHolder makeFNHolder(Message message, FollowUpReminderEmail fN) {
        FollowUpNotificationHolder fNH = new FollowUpNotificationHolder();
        Address[] recipients = message.getRecipients(Message.RecipientType.TO);

        for(Address a : recipients) {
            fNH.recipientAddresses.concat(a.getAddress()+", ");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy @ hh:mm a", Locale.CANADA);
        fNH.dateTime = sdf.toString();
        return fNH;
    }

    public String getRecipientAddresses() {
        return recipientAddresses;
    }

    public String getDateTime() {
        return dateTime;
    }
}
