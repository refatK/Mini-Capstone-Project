package com.fsck.k9.activity;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.fsck.k9.Account;
import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.Preferences;
import com.fsck.k9.activity.compose.MessageActions;
import com.fsck.k9.activity.compose.RecipientPresenter;
import com.fsck.k9.activity.compose.SaveScheduledMessageTask;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertEquals;

import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.helper.Contacts;
import com.fsck.k9.mail.internet.MimeMessage;
import timber.log.Timber;
import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class EditScheduledMessageUnitTest {

    //test SaveScheduledMessageTask

    @InjectMocks
    MessageCompose messageCompose;

    @Mock
    MimeMessage message;
    SaveScheduledMessageTask saveScheduledMessageTask;
    Account testAccount;
    Contacts contacts;
    Context context;
    Handler handler;
    RecipientPresenter recipientPresenter;

    //variables
    //variables used in onmessagebuildsuccess
    private boolean isDraft;
    private boolean isScheduledSaved;
    private long draftId;
    private boolean saveRemotely;
    private long scheduledId;
    private boolean messageSaved;


    @Before
    public void setUp()
    {
        isScheduledSaved = false;
        isDraft = true;
        messageSaved = false;
        messageCompose.setScheduledTest(isScheduledSaved);
        messageCompose.setMessageSavedTest(messageSaved);
    }

    @Test
    public void testEditedAndSaved()
    {
        //will set isscheduledsaved to true
        messageCompose.checkToSaveAndConfirmScheduledSaveTest();


        //check that boolean is now true
        assertEquals(true, messageCompose.getScheduledTest());

        //call onmessagebuildsuccess method to save the draft
        messageCompose.onMessageBuildSuccessTest(message, isDraft);


        //verify that messageSaved variable in method is true;
        assertEquals(true, messageCompose.getMessageSavedTest());

    }

    @Test
    public void testEditedAndCanceled()
    {
        //check that isscheduledsaved boolean is false
        assertEquals(false, isScheduledSaved);

        //call onmessagebuildsuccess method
        messageCompose.onMessageBuildSuccessTest(message, isDraft);

        //verify that messagesaved is false
        assertEquals(false, messageCompose.getMessageSavedTest());


    }


}