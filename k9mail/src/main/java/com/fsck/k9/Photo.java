package com.fsck.k9;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Photo")
public class Photo {
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "file_name")
    private String fileName;

    @Property(nameInDb = "answer")
    private String answer;

    @Property(nameInDb = "Choice_1")
    private String choice1;

    @Property(nameInDb = "Choice_2")
    private String choice2;

    @Property(nameInDb = "Choice_3")
    private String choice3;

    @Property(nameInDb = "Choice_4")
    private String choice4;

    @Generated(hash = 69623676)
    public Photo(Long id, String fileName, String answer, String choice1,
            String choice2, String choice3, String choice4) {
        this.id = id;
        this.fileName = fileName;
        this.answer = answer;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    @Generated(hash = 1043664727)
    public Photo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChoice1() {
        return this.choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return this.choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return this.choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return this.choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }
}
