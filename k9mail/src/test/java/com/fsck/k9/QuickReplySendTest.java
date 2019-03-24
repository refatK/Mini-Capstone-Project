package com.fsck.k9;

import android.view.View;
import android.widget.LinearLayout;

import com.fsck.k9.activity.MessageCompose;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(K9RobolectricTestRunner.class)
public class QuickReplySendTest {

    private MessageCompose messageComposeActivity;

    @Before
    public void setUp() throws Exception {
        messageComposeActivity = Robolectric.setupActivity(MessageCompose.class);
    }

    @Test
    public void actionIsQuickReply_HidesView() {
        LinearLayout messageComposeView = mock(LinearLayout.class);

        messageComposeActivity.hideView(messageComposeView);
        // If there was no intent for quick reply, don't hide
        verify(messageComposeView, never()).setVisibility(anyInt());

        messageComposeActivity.getIntent().putExtra(MessageCompose.EXTRA_QUICK_REPLY_MESSAGE, "This is a quick reply");
        messageComposeActivity.hideView(messageComposeView);
        // If there was, do hide
        verify(messageComposeView).setVisibility(View.INVISIBLE);
    }

    @Test
    public void actionIsQuickReply_MakesSendInstant() {
        //TODO maybe test processMessageToReplyTo() , make sure to make the method public if issues
        // Would check that the handle send thing happens
    }

    @Test
    public void quickReplySend_SendsWithSmallDelay() {
        //TODO Idk if possible, but maybe test the handleQuickReplySend() actuaaly takes 2 seconds
        // Once Again, make sure to make the method public if issues
    }
}
