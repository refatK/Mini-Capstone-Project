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
                int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);;
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);;

                if(year < currentYear
                        || (year == currentYear
                        && ((month < currentMonth) || (month == currentMonth && day < currentDay)))
                        || ((year == currentYear && month == currentMonth && day == currentDay)
                        && (hour < currentHour || (hour == currentHour && minute <= currentMinute)))
                        ){
                    Toast.makeText(getApplicationContext(), "Pick another date",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Setting time to: " + day + "/"
                            + (month + 1) + "/" + year + " | "  + hour + ":" + minute,
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //VERY IMPORTANT
        //MONTH IS GIVEN STARTING FROM 0
        TextView date = (TextView) findViewById(R.id.send_later_date);
        String strDate = (month + 1) + "/" + dayOfMonth + "/" + year;
        date.setText(strDate);
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
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
