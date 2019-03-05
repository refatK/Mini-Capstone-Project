package com.fsck.k9;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "scheduled_email")
public class ScheduledEmail {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "account_id")
    private String accountID;

    @Property(nameInDb = "email_id")
    private Long emailID;

    @Property(nameInDb = "scheduled_date_time")
    private Long scheduledDateTime;

    @Generated(hash = 493020854)
    public ScheduledEmail(Long id, String accountID, Long emailID,
            Long scheduledDateTime) {
        this.id = id;
        this.accountID = accountID;
        this.emailID = emailID;
        this.scheduledDateTime = scheduledDateTime;
    }

    @Generated(hash = 1427611298)
    public ScheduledEmail() {
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

    public Long getScheduledDateTime() {
        return this.scheduledDateTime;
    }

    public void setScheduledDateTime(Long scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

}