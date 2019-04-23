package com.fsck.k9._390Tests.Release1;

import com.fsck.k9.EmailAddress;
import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.MailingList;
import com.fsck.k9.view.RecipientSelectView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(K9RobolectricTestRunner.class)
public class RecipientSelectViewTest {

    private MailingList mailingListEmpty;
    private MailingList mailingList1;
    private MailingList mailingList2;

    private RecipientSelectView recipientSelectView;
    private List<MailingList> mailingLists;

    @Before
    public void setUp() throws Exception {
        // prepare mocks
        mailingListEmpty = mock(MailingList.class);
        mailingList1 = mock(MailingList.class);
        mailingList2 = mock(MailingList.class);

        // prepare some emails
        EmailAddress email1 = new EmailAddress(0L, 0L, "email1@example.com");
        EmailAddress email2 = new EmailAddress(1L, 1L, "email2@example.com");
        EmailAddress email3 = new EmailAddress(2L, 1L, "email3@example.com");

        // prepare mailingLists
        mailingLists = new ArrayList<>();

        when(mailingListEmpty.getName()).thenReturn("MailingListEmpty");
        when(mailingListEmpty.getEmails()).thenReturn(Collections.<EmailAddress>emptyList());
        mailingLists.add(mailingListEmpty);

        when(mailingList1.getName()).thenReturn("MailingList1");
        when(mailingList1.getEmails()).thenReturn(Collections.singletonList(email1));
        mailingLists.add(mailingList1);

        when(mailingList2.getName()).thenReturn("MailingList2");
        when(mailingList2.getEmails()).thenReturn(new ArrayList<>(Arrays.asList(email2, email3)));
        mailingLists.add(mailingList2);

        // using mocks to create the class to test
        recipientSelectView = new RecipientSelectView(RuntimeEnvironment.application.getApplicationContext(), mailingLists);
    }

    @Test
    public void inputText_email_returnsRecipientWithAddress() {
        RecipientSelectView.Recipient oneEmailRecipient = recipientSelectView.defaultObject("test@example.com");

        assertEquals(1, oneEmailRecipient.addresses.size());
        assertEquals("test@example.com", oneEmailRecipient.addresses.get(0).getAddress());


    }

    @Test
    public void inputText_emptyMailingList_returnNull() {
        RecipientSelectView.Recipient emptyMailingListRecipient = recipientSelectView.defaultObject("MailingListEmpty");

        assertNull(emptyMailingListRecipient);
    }

    @Test
    public void inputText_nonEmptyMailingList_returnRecipientWithAddresses() {
        RecipientSelectView.Recipient nonEmptyMailingListRecipient = recipientSelectView.defaultObject("MailingList1");

        assertEquals(1, nonEmptyMailingListRecipient.addresses.size());
        assertEquals("email1@example.com", nonEmptyMailingListRecipient.addresses.get(0).getAddress());

        nonEmptyMailingListRecipient = recipientSelectView.defaultObject("MailingList2");

        assertEquals(2, nonEmptyMailingListRecipient.addresses.size());
        assertEquals("email2@example.com", nonEmptyMailingListRecipient.addresses.get(0).getAddress());
        assertEquals("email3@example.com", nonEmptyMailingListRecipient.addresses.get(1).getAddress());
    }

    @Test
    public void inputText_mailingListWithWrongCapitalization_returnRecipientWithAddresses() {
        RecipientSelectView.Recipient wrongCapMailingListRecipient = recipientSelectView.defaultObject("mailinglist2");

        assertEquals(2, wrongCapMailingListRecipient.addresses.size());
        assertEquals("email2@example.com", wrongCapMailingListRecipient.addresses.get(0).getAddress());
        assertEquals("email3@example.com", wrongCapMailingListRecipient.addresses.get(1).getAddress());
    }

    @Test
    public void InputText_InvalidMailingList_returnNull() {
        RecipientSelectView.Recipient InvalidMailngListRecipient = recipientSelectView.defaultObject("testexample");

        assertNull(InvalidMailngListRecipient);
    }
}