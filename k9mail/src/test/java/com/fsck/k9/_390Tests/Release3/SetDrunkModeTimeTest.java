package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.activity.SetDrunkModeTime;
import com.fsck.k9.activity.setup.DrunkModeSettings;
import com.fsck.k9.fragment.DrunkModeTimePicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(K9RobolectricTestRunner.class)
public class SetDrunkModeTimeTest {

    //To be tested
    private DrunkModeTimePicker drunkModeTimePicker;
    private SetDrunkModeTime setDrunkModeTime;

    @Before
    public void setUp(){
        drunkModeTimePicker = mock(DrunkModeTimePicker.class);
        setDrunkModeTime = mock(SetDrunkModeTime.class);
    }

    @Test
    public void testDateToCalendarFormat(){

    }

    @Test
    public void testConfirmSetTime(){

    }

    @Test
    public void testOnTimeSet(){

    }

}
