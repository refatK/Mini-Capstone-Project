package com.fsck.k9;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "follow_up_reminder_email")
public class FollowUpReminderEmail {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "account_id")
    private String accountID;

    @Property(nameInDb = "email_id")
    private Long emailID;

    @Property(nameInDb = "reminder_date_time")
    private Long reminderDateTime;

    @Generated(hash = 2121669676)
    public FollowUpReminderEmail(Long id, String accountID, Long emailID,
            Long reminderDateTime) {
        this.id = id;
        this.accountID = accountID;
        this.emailID = emailID;
        this.reminderDateTime = reminderDateTime;
    }

    @Generated(hash = 1415954266)
    public FollowUpReminderEmail() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public Long getEmailID() {
        return this.emailID;
    }

    public void setEmailID(Long emailID) {
        this.emailID = emailID;
    }

    public Long getReminderDateTime() {
        return this.reminderDateTime;
    }

    public void setReminderDateTime(Long reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
    }
}
