package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.activity.setup.DrunkModeSettings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(K9RobolectricTestRunner.class)
public class DrunkModeSettingsTest {

    //to be tested
    private DrunkModeSettings drunkModeSettings;

    @Before
    public void setUp(){
        drunkModeSettings = mock(DrunkModeSettings.class);
    }

    @Test
    public void testDateToCalendarFormat(){

    }
}
