package com.fsck.k9;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "email_address")
public class EmailAddress {

    @Id(autoincrement = true)
    private long id;

    @Property(nameInDb = "email")
    private String email;

    @Generated(hash = 1224216252)
    public EmailAddress(long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Generated(hash = 470303096)
    public EmailAddress() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
