package com.fsck.k9.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fsck.k9.DaoSession;
import com.fsck.k9.R;
import com.fsck.k9.fragment.SendLaterDatePicker;
import com.fsck.k9.fragment.SendLaterTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SetFollowUpReminderDateAndTime extends K9Activity {

    private Button setTimeButton;
    private Button setDateButton;
    private Button setDateAndTimeButton;

    private TextView chosenDateTextView;
    private TextView chosenTimeTextView;

    private Calendar chosenDateAndTime;

    private DaoSession daoSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_follow_up_reminder_date_and_time);
        setTimeButton = (Button) findViewById(R.id.reminder_set_time_button);
        setDateButton = (Button) findViewById(R.id.reminder_set_date_button);
        setDateAndTimeButton = (Button) findViewById(R.id.reminder_set_date_and_time_button);

    }

}
