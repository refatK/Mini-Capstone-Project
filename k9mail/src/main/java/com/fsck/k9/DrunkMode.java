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

    @Property(nameInDb = "start_time")
    private Date startTime;

    @Property(nameInDb = "end_time")
    private Date endTime;

    @Generated(hash = 1801453768)
    public DrunkMode(Long id, Boolean isDrunk, Date startTime, Date endTime) {
        this.id = id;
        this.isDrunk = isDrunk;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
