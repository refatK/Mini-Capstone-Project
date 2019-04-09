package com.fsck.k9.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.fsck.k9.DaoSession;
import com.fsck.k9.R;

import java.util.Calendar;


public class SetFollowUpReminderDateAndTime extends K9Activity {

    private Button setTimeButton;
    private Button setDateButton;
    private Button setDateAndTimeButton;

    private TextView chosenDateTextView;
    private TextView chosenTimeTextView;

    private DaoSession daoSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_follow_up_reminder_date_and_time);
    }

}
