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
import com.fsck.k9.fragment.SendLaterDatePicker;
import com.fsck.k9.fragment.SendLaterTimePicker;

import java.util.Calendar;

public class SetDateAndTime  extends K9Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private Button setTimeButton;
    private Button setDateButton;
    private Button setDateAndTimeButton;

    private TextView chosenDateTextView;
    private TextView chosenTimeTextView;

    private Calendar chosenDateAndTime;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_later_set_date_and_time);
        setDateButton = (Button) findViewById(R.id.send_later_set_date_button);
        setTimeButton = (Button) findViewById(R.id.send_later_set_time_button);
        setDateAndTimeButton = (Button) findViewById(R.id.send_later_set_date_and_time_button);

        chosenDateTextView = (TextView) findViewById(R.id.send_later_date);
        String strDate = "MM/DD/YYYY";
        chosenDateTextView.setText(strDate);

        chosenTimeTextView = (TextView) findViewById(R.id.send_later_time);
        String strTime = "hh:mm";
        chosenTimeTextView.setText(strTime);

        chosenDateAndTime = Calendar.getInstance();

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
                Calendar currentDateAndTime = Calendar.getInstance();
                long currentDateAndTimeInMilis = currentDateAndTime.getTimeInMillis();
                long chosenDateAndTimeInMilis = chosenDateAndTime.getTimeInMillis();

                if(chosenDateAndTimeInMilis <= currentDateAndTimeInMilis){
                    Toast.makeText(getApplicationContext(), "Pick another date",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Setting time to: "
                                    + (chosenDateAndTime.get(Calendar.MONTH) + 1) + "/"
                                    + chosenDateAndTime.get(Calendar.DAY_OF_MONTH) + "/"
                                    + chosenDateAndTime.get(Calendar.YEAR) + " @ "
                                    + chosenDateAndTime.get(Calendar.HOUR_OF_DAY) + ":"
                                    + ((chosenDateAndTime.get(Calendar.MINUTE) < 10) ? "0" : "")
                                    + (chosenDateAndTime.get(Calendar.MINUTE)),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //VERY IMPORTANT
        //MONTH IS GIVEN STARTING FROM 0
        String strDate = (month + 1) + "/" + dayOfMonth + "/" + year;
        this.chosenDateAndTime.set(Calendar.YEAR, year);
        this.chosenDateAndTime.set(Calendar.MONTH, month);
        this.chosenDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        chosenDateTextView.setText(strDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String strTime = (hourOfDay%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hourOfDay > 12 ? "PM" : "AM");
        this.chosenDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        this.chosenDateAndTime.set(Calendar.MINUTE, minute);
        chosenTimeTextView.setText(strTime);
    }

    public void supersedeChosenDateTextView(TextView chosenDateTextView){
        this.chosenDateTextView = chosenDateTextView;
    }
    public void supersedeChosenTimeTextView(TextView chosenTimeTextView){
        this.chosenTimeTextView = chosenTimeTextView;
    }
    public void supersedeChosenDateAndTime(Calendar chosenDateAndTime){
        this.chosenDateAndTime = chosenDateAndTime;
    }
}
