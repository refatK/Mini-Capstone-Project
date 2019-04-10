package com.fsck.k9.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fsck.k9.DaoSession;
import com.fsck.k9.R;
import com.fsck.k9.fragment.FollowUpReminderSelectDate;
import com.fsck.k9.fragment.FollowUpReminderSelectTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SetFollowUpReminderDateAndTime extends K9Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

        Date dateIncomingIntent = (Date)getIntent().getSerializableExtra("currentDate");
        chosenDateTextView = (TextView) findViewById(R.id.reminder_date);
        chosenTimeTextView = (TextView) findViewById(R.id.reminder_time);
        String strDate;
        String strTime;
        chosenDateAndTime = Calendar.getInstance();

        if(dateIncomingIntent == null) {

            strDate = "MM/DD/YYYY";
            chosenDateTextView.setText(strDate);

            strTime = "hh:mm";
            chosenTimeTextView.setText(strTime);

        }
        else{

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            strDate = inputDateFormat.format(dateIncomingIntent);
            chosenDateTextView.setText(strDate);

            SimpleDateFormat inputTimeFormat = new SimpleDateFormat ("h:mm a");
            strTime = inputTimeFormat.format(dateIncomingIntent);
            chosenTimeTextView.setText(strTime);

            this.chosenDateAndTime.setTime(dateIncomingIntent);

        }
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new FollowUpReminderSelectDate();
                newFragment.show(getFragmentManager(), "selectDate");
            }
        });
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new FollowUpReminderSelectTime();
                newFragment.show(getFragmentManager(), "selectTime");
            }
        });
        setDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateAndTime();
            }
        });
    }

    public void setDateAndTime() {
        Calendar currentDateAndTime = Calendar.getInstance();
        long currentDateAndTimeInMilis = currentDateAndTime.getTimeInMillis();
        long chosenDateAndTimeInMilis = chosenDateAndTime.getTimeInMillis();

        if(chosenDateAndTimeInMilis <= currentDateAndTimeInMilis){
            Toast.makeText(getApplicationContext(), "Please choose another date.",
                Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Setting time to: "
                    + (chosenDateAndTime.get(Calendar.MONTH) + 1) + "/"
                    + chosenDateAndTime.get(Calendar.DAY_OF_MONTH) + "/"
                    + chosenDateAndTime.get(Calendar.YEAR) + " @ "
                    + (chosenDateAndTime.get(Calendar.HOUR_OF_DAY)%12 == 0 ?
                    "12" : chosenDateAndTime.get(Calendar.HOUR_OF_DAY)%12) + ":"
                    + ((chosenDateAndTime.get(Calendar.MINUTE) < 10) ? "0" : "")
                    + (chosenDateAndTime.get(Calendar.MINUTE))
                    + (chosenDateAndTime.get(Calendar.HOUR_OF_DAY) >= 12 ? "PM" : "AM"),
                Toast.LENGTH_SHORT).show();
            if (!getIntent().getBooleanExtra("testingSetFollowUpReminderDateAndTime", false)) {
                this.saveAndFinish();
            }

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String strDate = (month + 1) + "/" + dayOfMonth + "/" + year;
        this.chosenDateAndTime.set(Calendar.YEAR, year);
        this.chosenDateAndTime.set(Calendar.MONTH, month);
        this.chosenDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        chosenDateTextView.setText(strDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String strTime = (hourOfDay%12 == 0 ? "12" : hourOfDay%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hourOfDay >= 12 ? "PM" : "AM");
        this.chosenDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.chosenDateAndTime.set(Calendar.MINUTE, minute);
        chosenTimeTextView.setText(strTime);
    }

    public void saveAndFinish() {
        Intent data = new Intent();
        data.putExtra("FollowUpReminderDate", chosenDateAndTime.getTimeInMillis());
        setResult(RESULT_OK, data);
        super.finish();
    }
}
