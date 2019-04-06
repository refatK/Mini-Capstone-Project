package com.fsck.k9._390Tests.Release3;

import android.content.Context;
import android.widget.TextView;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.activity.SetDrunkModeTime;
import com.fsck.k9.activity.setup.DrunkModeSettings;
import com.fsck.k9.fragment.DrunkModeTimePicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(K9RobolectricTestRunner.class)
public class SetDrunkModeTimeTest {

    //To be tested
    private DrunkModeTimePicker drunkModeTimePicker;
    private SetDrunkModeTime setDrunkModeTime;

    private Date testedTime;
    private Integer hourOfDay;
    private Integer minute;

    private Calendar calendar;
    private TextView textView;
    private String strTime;

    @Before
    public void setUp(){
        drunkModeTimePicker = new DrunkModeTimePicker();
        setDrunkModeTime = new SetDrunkModeTime();
        calendar = Calendar.getInstance();
        calendar.clear();

        textView = mock(TextView.class);

        drunkModeTimePicker.setCurrentCalendar(calendar);
        drunkModeTimePicker.setCurrentTextView(textView);

        Calendar testingTime = Calendar.getInstance();
        testingTime.clear();
        hourOfDay = 23;
        minute = 58;
        testingTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        testingTime.set(Calendar.MINUTE, minute);
        testedTime = testingTime.getTime();

        strTime = (hourOfDay%12 == 0 ? "12" : hourOfDay%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hourOfDay >= 12 ? " PM" : " AM");
    }

    @Test
    public void testDateToCalendarFormat(){
        assertThat(setDrunkModeTime.dateToCalendarFormat(testedTime), is(strTime));
    }

    @Test
    public void testOnTimeSet(){
        drunkModeTimePicker.onTimeSet(null, hourOfDay, minute);
        assertThat(drunkModeTimePicker.getCurrentCalendar(), is(calendar));
        assertThat(drunkModeTimePicker.getCurrentTextView(), is(textView));
        verify(textView).setText(strTime);
    }

}
