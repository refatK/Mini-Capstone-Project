package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.Account;
import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(K9RobolectricTestRunner.class)
public class FollowupCancelWhenSatisfiedTest {

    private static final Address[] EMPTY_ADDRESSES = new Address[0];
    private static final Address self = new Address("self@example.com");
    private static final Address other = new Address("other@example.com");

    private Message messageMock;
    private Account accountMock;


    @Before
    public void setUp() throws Exception {
        messageMock = mock(Message.class);

        accountMock = mock(Account.class);
        when(accountMock.isAnIdentity(new Address[]{self})).thenReturn(true);
        when(accountMock.isAnIdentity(new Address[]{other})).thenReturn(false);
    }

    @Test
    public void isToSelf_passesWhenMessageFromSelfOnlyToSelf() {
        when(messageMock.getFrom()).thenReturn(new Address[]{self});
        when(accountMock.getEmail()).thenReturn(self.getAddress());

        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{self});
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(EMPTY_ADDRESSES);
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(EMPTY_ADDRESSES);

        assertTrue(MessagingController.isToSelf(messageMock, accountMock));
    }

    @Test
    public void isToSelf_failsWhenMessageFromAnotherEmail() {
        when(messageMock.getFrom()).thenReturn(new Address[]{other});
        when(accountMock.getEmail()).thenReturn(other.getAddress());

        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{self});
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(EMPTY_ADDRESSES);
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(EMPTY_ADDRESSES);

        assertFalse(MessagingController.isToSelf(messageMock, accountMock));
    }

    @Test
    public void isToSelf_failsWhenToDifferentUser() {
        when(messageMock.getFrom()).thenReturn(new Address[]{self});
        when(accountMock.getEmail()).thenReturn(self.getAddress());

        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{other});
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(EMPTY_ADDRESSES);
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(EMPTY_ADDRESSES);

        assertFalse(MessagingController.isToSelf(messageMock, accountMock));

        // also check if email has both to self and other
        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{self, other});
        assertFalse(MessagingController.isToSelf(messageMock, accountMock));
    }

    @Test
    public void isToSelf_failsWhenCCOrBCCNonEmpty() {
        when(messageMock.getFrom()).thenReturn(new Address[]{self});
        when(accountMock.getEmail()).thenReturn(self.getAddress());

        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{self});
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(new Address[]{other});
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(EMPTY_ADDRESSES);

        assertFalse(MessagingController.isToSelf(messageMock, accountMock));

        // also check non empty BCC case
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(EMPTY_ADDRESSES);
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(new Address[]{other});
        assertFalse(MessagingController.isToSelf(messageMock, accountMock));
    }
}
