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
import com.fsck.k9.R;

public class SetDateAndTime  extends K9Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private Button setTimeButton;
    private Button setDateButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_later_set_date_and_time);
        setTimeButton = (Button) findViewById(R.id.send_later_set_date_button);
        setDateButton = (Button) findViewById(R.id.send_later_set_time_button);

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
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView date = (TextView) findViewById(R.id.send_later_date);
        String strDate = dayOfMonth + "/" + month + "/" + year;
        date.setText(strDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView time = (TextView) findViewById(R.id.send_later_time);
        String strDate = hourOfDay + ":" + minute;
        time.setText(strDate);
    }
}
