package com.fsck.k9.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DrunkModeTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    TextView currentTextView;
    Context currentContext;
    Calendar currentCalendar;

    public DrunkModeTimePicker(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        int hour = 0;
        int minute = 0;

        TimePickerDialog tpd = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        tpd.setTitle("Set time for Drunk Mode");
        return tpd;
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String strTime = (hourOfDay%12 == 0 ? "12" : hourOfDay%12) + ":"
                + ((minute < 10) ? "0" + minute : minute) + (hourOfDay >= 12 ? " PM" : " AM");
        this.currentCalendar.clear();
        this.currentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.currentCalendar.set(Calendar.MINUTE, minute);
        currentTextView.setText(strTime);
    }

    public void setCurrentTextView(TextView currentTextView) {
        this.currentTextView = currentTextView;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

}
