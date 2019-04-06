package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.activity.setup.DrunkModeSettings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(K9RobolectricTestRunner.class)
public class DrunkModeSettingsTest {

    //to be tested
    private DrunkModeSettings drunkModeSettings;

    private Date testedTime;
    private Integer hourOfDay;
    private Integer minute;

    @Before
    public void setUp(){
        drunkModeSettings = new DrunkModeSettings();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        hourOfDay = 23;
        minute = 58;
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        testedTime = calendar.getTime();
    }

    @Test
    public void testDateToCalendarFormat(){
        String strTime = (hourOfDay%12 == 0 ? "12" : hourOfDay%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hourOfDay >= 12 ? " PM" : " AM");
        assertThat(drunkModeSettings.dateToCalendarFormat(testedTime), is(strTime));
    }
}
