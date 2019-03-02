package com.fsck.k9.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fsck.k9.R;

import java.util.Calendar;

public class SetDateAndTime  extends K9Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private Button setTimeButton;
    private Button setDateButton;
    private Button setDateAndTimeButton;

    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int minute = 0;
    private int hour = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_later_set_date_and_time);
        setTimeButton = (Button) findViewById(R.id.send_later_set_date_button);
        setDateButton = (Button) findViewById(R.id.send_later_set_time_button);
        setDateAndTimeButton = (Button) findViewById(R.id.send_later_set_date_and_time_button);


        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SendLaterDatePicker();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SendLaterTimePicker();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        setDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);;
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);;
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year + " | "  + hour + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView date = (TextView) findViewById(R.id.send_later_date);
        String strDate = dayOfMonth + "/" + month + "/" + year;
        date.setText(strDate);
        this.year = year;
        this.month = month;
        this.day = dayOfMonth
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView time = (TextView) findViewById(R.id.send_later_time);
        String strDate = hourOfDay + ":" + minute;
        time.setText(strDate);
        this.hour = hourOfDay;
        this.minute = minute;
    }
}
