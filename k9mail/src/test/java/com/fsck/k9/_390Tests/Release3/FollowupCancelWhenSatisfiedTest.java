package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.Account;
import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Folder;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.mailstore.LocalStore;

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

        // message to another email
        assertFalse(MessagingController.isToSelf(messageMock, accountMock));

        // should still fail when email has both to self and another email
        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{self, other});
        assertFalse(MessagingController.isToSelf(messageMock, accountMock));
    }

    @Test
    public void isToSelf_failsWhenCcOrBccNonEmpty() {
        when(messageMock.getFrom()).thenReturn(new Address[]{self});
        when(accountMock.getEmail()).thenReturn(self.getAddress());

        when(messageMock.getRecipients(Message.RecipientType.TO)).thenReturn(new Address[]{self});
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(new Address[]{other});
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(EMPTY_ADDRESSES);

        // non-empty CC case
        assertFalse(MessagingController.isToSelf(messageMock, accountMock));

        // also check non empty BCC case
        when(messageMock.getRecipients(Message.RecipientType.CC)).thenReturn(EMPTY_ADDRESSES);
        when(messageMock.getRecipients(Message.RecipientType.BCC)).thenReturn(new Address[]{other});
        assertFalse(MessagingController.isToSelf(messageMock, accountMock));
    }

    @Test
    public void isRepliedBy_passesWhenReplyUsesReFormat() {
        LocalMessage message = new LocalMessage(mock(LocalStore.class), "0", mock(Folder.class));
        message.setMessageId("0");
        message.setSubject("Subject");

        LocalMessage replyMessage = new LocalMessage(mock(LocalStore.class), "1", mock(Folder.class));
        replyMessage.setMessageId("1");
        replyMessage.setSubject("RE: Subject");

        assertTrue(message.isRepliedBy(replyMessage));

        // case shouldn't matter either
        replyMessage.setSubject("re: Subject");
        assertTrue(message.isRepliedBy(replyMessage));

        // space shouldn't matter
        replyMessage.setSubject("RE:Subject");
        assertTrue(message.isRepliedBy(replyMessage));

        // shouldn't work if check is flipped
        replyMessage.setSubject("RE: Subject");
        assertFalse(replyMessage.isRepliedBy(message));

        // shouldn't work just because base message starts with RE:
        message.setSubject("RE: Subject");
        assertFalse(message.isRepliedBy(replyMessage));

        // but re: format still works for base message with RE: if in expected format
        replyMessage.setSubject("RE: RE: Subject");
        assertTrue(message.isRepliedBy(replyMessage));
    }

    @Test
    public void isRepliedBy_passesWhenReferencedByRepliedMessage() {
        LocalMessage message = new LocalMessage(mock(LocalStore.class), "0", mock(Folder.class));
        message.setMessageId("0");
        message.setSubject("Subject");

        LocalMessage replyMessage = new LocalMessage(mock(LocalStore.class), "1", mock(Folder.class));
        replyMessage.setMessageId("1");
        replyMessage.setSubject("Reply to Subject Not In reply format");
        replyMessage.setReferences("0");

        assertTrue(message.isRepliedBy(replyMessage));

        // should still work with a deeper reference
        replyMessage.setReferences("1, 2, 3, 0, 5");
        assertTrue(message.isRepliedBy(replyMessage));
    }

    @Test
    public void isRepliedBy_failsWhenNeitherReferencedNorReFormat() {
        LocalMessage message = new LocalMessage(mock(LocalStore.class), "0", mock(Folder.class));
        message.setMessageId("0");
        message.setSubject("Subject");

        LocalMessage replyMessage = new LocalMessage(mock(LocalStore.class), "1", mock(Folder.class));
        replyMessage.setMessageId("1");
        replyMessage.setSubject("Reply to Subject Not In reply format");

        assertFalse(message.isRepliedBy(replyMessage));
    }
}
