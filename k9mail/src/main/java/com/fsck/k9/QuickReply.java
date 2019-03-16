package com.fsck.k9;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "quick_reply")
public class QuickReply {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "body")
    private String body;

    @Generated(hash = 1375013446)
    public QuickReply(Long id, String body) {
        this.id = id;
        this.body = body;
    }

    @Generated(hash = 1548336011)
    public QuickReply() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
