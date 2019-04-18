package com.fsck.k9;

import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FollowUpNotificationHolder {
    private String recipientAddresses;
    private String dateTime;
    private String subject;


    public FollowUpNotificationHolder() {
        recipientAddresses = "";
        dateTime = "";
        subject = "";
    }

    public static FollowUpNotificationHolder makeFNHolder(Message message, FollowUpReminderEmail fN) {
        FollowUpNotificationHolder fNH = new FollowUpNotificationHolder();
        Address[] recipients = message.getRecipients(Message.RecipientType.TO);

        for(Address a : recipients) {
            fNH.recipientAddresses += (a.getAddress()+", ");
        }
        fNH.recipientAddresses
                = fNH.recipientAddresses.substring(0, fNH.recipientAddresses.length()-2);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy @ hh:mm a", Locale.CANADA);
        fNH.dateTime = sdf.format(new Date(fN.getReminderDateTime()));

        fNH.subject = message.getSubject();

        return fNH;
    }

    public String getRecipientAddresses() {
        return recipientAddresses;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setRecipientAddresses(String recipientAddresses) {
        this.recipientAddresses = recipientAddresses;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
