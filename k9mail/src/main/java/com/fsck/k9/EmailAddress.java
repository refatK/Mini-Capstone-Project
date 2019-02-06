package com.fsck.k9;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "email_address")
public class EmailAddress {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "mailing_list_id")
    private Long mailingListID;

    @Property(nameInDb = "email")
    private String email;

    @Generated(hash = 769759947)
    public EmailAddress(Long id, Long mailingListID, String email) {
        this.id = id;
        this.mailingListID = mailingListID;
        this.email = email;
    }

    @Generated(hash = 470303096)
    public EmailAddress() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMailingListID() {
        return this.mailingListID;
    }

    public void setMailingListID(Long mailingListID) {
        this.mailingListID = mailingListID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
