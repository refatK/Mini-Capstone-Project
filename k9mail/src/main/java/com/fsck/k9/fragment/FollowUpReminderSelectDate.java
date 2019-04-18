package com.fsck.k9.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.fsck.k9.activity.SetFollowUpReminderDateAndTime;

import java.util.Calendar;


public class FollowUpReminderSelectDate extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), (SetFollowUpReminderDateAndTime)getActivity(), year, month, day);
        dpd.setTitle("Set Date for Follow-Up Reminder");
        return dpd;
    }

}
