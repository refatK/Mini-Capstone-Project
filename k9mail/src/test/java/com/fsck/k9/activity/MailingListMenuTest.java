package com.fsck.k9.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.MailingList;
import com.fsck.k9.activity.setup.MailingListMenu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(K9RobolectricTestRunner.class)
public class MailingListMenuTest{

    MailingListMenu mailingListMenu;
    MailingListMenu mailingListMenuWithRefresh;
    List<MailingList> testMailingLists;

    @Before
    public void setup(){
        Bundle b = new Bundle();
        b.putBoolean("under test", true);

        Bundle b2 = new Bundle();
        b2.putBoolean("refresh needed", true);

        testMailingLists = new ArrayList<MailingList>();

        testMailingLists.add(new MailingList(null, "TestList1"));
        testMailingLists.add(new MailingList(null, "TestList2"));
        testMailingLists.add(new MailingList(null, "TestList3"));

        mailingListMenu = Robolectric.buildActivity(MailingListMenu.class).create(b).get();
        mailingListMenuWithRefresh = Robolectric.buildActivity(MailingListMenu.class)
                .create(b2)
                    .get();
    }

    //makes sure that the items in the list are the names of every mailing list and are clickable.
    @Test
    public void all_Mailing_Lists_Are_In_The_List(){
       ListView mailingListMenuView =  mailingListMenu.getListView();
       for(int i = 0; i < testMailingLists.size(); i++) {
           assertEquals(testMailingLists.get(i).getName(),
                   mailingListMenuView.getAdapter().getItem(i));
       }
       assertTrue(mailingListMenuView.getAdapter().areAllItemsEnabled());
    }

    //makes sure the list has refreshed without crashing.
    @Test
    public void list_Refreshes_When_Needed(){
        ListView mailingListMenuView =  mailingListMenu.getListView();
        for(int i = 0; i < testMailingLists.size(); i++) {
            assertEquals(testMailingLists.get(i).getName(),
                    mailingListMenuView.getAdapter().getItem(i));
        }
        assertTrue(mailingListMenuView.getAdapter().areAllItemsEnabled());
        assertFalse(mailingListMenuWithRefresh.getIntent().getBooleanExtra("refresh needed", false));
    }

}
