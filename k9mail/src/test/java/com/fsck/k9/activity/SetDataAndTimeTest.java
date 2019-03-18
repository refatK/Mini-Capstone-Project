package com.fsck.k9.activity;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fsck.k9.K9RobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(K9RobolectricTestRunner.class)
public class SetDataAndTimeTest {

    // To be tested
    private SetDateAndTime setDateAndTime;

    // Mocked
    private Calendar chosenDateAndTime;
    private TextView chosenDateTextView;
    private TextView chosenTimeTextView;
    private DatePicker dateView;
    private TimePicker timeView;

    // Variables
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String strDate;
    private String strTime;

    @Before
    public void setUp() throws Exception{

        // Date: 1/1/2019
        year = 2019;
        //When using the Calendar class counts months starting by 0
        month = 0;
        day = 1;

        // Time: 14:30
        hour = 14;
        minute = 30;

        chosenDateTextView = mock(TextView.class);
        chosenTimeTextView = mock(TextView.class);
        chosenDateAndTime = mock(Calendar.class);
        dateView = mock(DatePicker.class);
        timeView = mock(TimePicker.class);

        setDateAndTime = new SetDateAndTime();
        setDateAndTime.supersedeChosenDateTextView(chosenDateTextView);
        setDateAndTime.supersedeChosenTimeTextView(chosenTimeTextView);
        setDateAndTime.supersedeChosenDateAndTime(chosenDateAndTime);
    }

    @Test
    public void testOnDateSet(){
        strDate = (month + 1) + "/" + day + "/" + year;

        setDateAndTime.onDateSet(dateView, year, month, day);

        verify(chosenDateTextView).setText(strDate);
        verify(chosenDateAndTime).set(Calendar.YEAR, year);
        verify(chosenDateAndTime).set(Calendar.MONTH, month);
        verify(chosenDateAndTime).set(Calendar.DAY_OF_MONTH, day);
    }

    @Test
    public void testOnTimeSet(){
        strTime = (hour%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hour > 12 ? "PM" : "AM");

        setDateAndTime.onTimeSet(timeView, hour, minute);

        verify(chosenTimeTextView).setText(strTime);
        verify(chosenDateAndTime).set(Calendar.HOUR_OF_DAY, hour);
        verify(chosenDateAndTime).set(Calendar.MINUTE, minute);
    }
}
