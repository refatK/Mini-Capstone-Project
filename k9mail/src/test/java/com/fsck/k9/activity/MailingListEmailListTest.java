package com.fsck.k9.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.fsck.k9.EmailAddress;
import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.activity.setup.MailingListEmailListMenu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(K9RobolectricTestRunner.class)
public class MailingListEmailListTest {

    private List<EmailAddress> testEmailList;
    private MailingListEmailListMenu emailListMenu;

    @Before
    public void setup(){

        Bundle b1 = new Bundle();
        b1.putBoolean("testToggle", true);

        testEmailList = new ArrayList<>();

        testEmailList.add(new EmailAddress(null, null, "test1@test.com"));
        testEmailList.add(new EmailAddress(null, null, "test2@test.com"));
        testEmailList.add(new EmailAddress(null, null, "test3@test.com"));

        emailListMenu = Robolectric.buildActivity(MailingListEmailListMenu.class).create(b1).get();
    }

    @Test
    public void allEmailsAreInTheList(){
        ListView emailListView = emailListMenu.getListView();
        for(int i = 0; i < testEmailList.size(); i++){
            assertThat(testEmailList.get(i).getEmail(), is(emailListView.getAdapter().getItem(i)));
        }
        assertThat(emailListView.getAdapter().areAllItemsEnabled(), is(true));
    }
}
