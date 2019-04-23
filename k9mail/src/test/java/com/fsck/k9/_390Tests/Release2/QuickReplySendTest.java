package com.fsck.k9._390Tests.Release2;

import android.view.View;
import android.widget.LinearLayout;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.activity.MessageCompose;

import static junit.framework.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;
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
    public void quickReplySend_SendsWithSmallDelay() {
        // sending causes this to be set true
        messageComposeActivity.changesMadeSinceLastSave = true;

        // do quick reply send
        messageComposeActivity.handleQuickReplySend();

        // check still not change because not run instantly
        assertTrue(messageComposeActivity.changesMadeSinceLastSave);

        //assert finished after expected send
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        assertFalse(messageComposeActivity.isFinishing());
                    }
                },
                2000
        );
    }
}
