package com.fsck.k9.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fsck.k9.activity.SetDateAndTime;

import java.util.Calendar;

public class SendLaterTimePicker extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(getActivity(), (SetDateAndTime) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
        tpd.setTitle("Set time for sending later");
        return tpd;
    }

}
