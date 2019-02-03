package com.fsck.k9;

import java.util.ArrayList;
import java.util.List;

public class MailingList {
    private String name;
    private List<String> emails;

    public MailingList(String MLname){
        this.name = MLname;
        this.emails = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setName(String name) {
        this.name = name;
    }
}
