package com.fsck.k9.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.fsck.k9.activity.SetFollowUpReminderDateAndTime;

import java.util.Calendar;

public class FollowUpReminderSelectTime extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(getActivity(), (SetFollowUpReminderDateAndTime) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
        tpd.setTitle("Set Time for Follow-up Reminder");
        return tpd;
    }

}
