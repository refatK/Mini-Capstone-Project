package com.fsck.k9;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity(nameInDb = "drunk_mode")
public class DrunkMode {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "is_drunk")
    private Boolean isDrunk;

    @Property(nameInDb = "time")
    private Date time;

    @Generated(hash = 405359390)
    public DrunkMode(Long id, Boolean isDrunk, Date time) {
        this.id = id;
        this.isDrunk = isDrunk;
        this.time = time;
    }

    @Generated(hash = 1874578310)
    public DrunkMode() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDrunk() {
        return this.isDrunk;
    }

    public void setIsDrunk(Boolean isDrunk) {
        this.isDrunk = isDrunk;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
