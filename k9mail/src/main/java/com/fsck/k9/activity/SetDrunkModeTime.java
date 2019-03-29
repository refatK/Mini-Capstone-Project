package com.fsck.k9.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fsck.k9.fragment.DrunkModeTimePicker;

import java.util.Calendar;

import com.fsck.k9.R;

public class SetDrunkModeTime extends K9Activity {

    private Button setStarTime;
    private Button setEndTime;
    private Button setTime;

    private TextView startTimeText;
    private TextView endTimeText;

    private Calendar chosenStartTime;
    private Calendar chosenEndTime;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_drunk_mode_time);

        setStarTime = (Button) findViewById(R.id.drunk_mode_set_start_time_button);
        setEndTime = (Button) findViewById(R.id.drunk_mode_set_end_time_button);
        setTime = (Button) findViewById(R.id.drunk_mode_set_time_button);

        startTimeText = (TextView) findViewById(R.id.drunk_mode_start_time);
        endTimeText = (TextView) findViewById(R.id.drunk_mode_end_time);

        String strStartTime;
        String strEndTime;

        chosenStartTime = Calendar.getInstance();
        chosenEndTime = Calendar.getInstance();

        //TODO: Call DATABASE

        setStarTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DrunkModeTimePicker();
                ((DrunkModeTimePicker) newFragment).setCurrentContext(getActivity());
                ((DrunkModeTimePicker) newFragment).setCurrentCalendar(chosenStartTime);
                ((DrunkModeTimePicker) newFragment).setCurrentTextView(startTimeText);
                newFragment.show(getFragmentManager(), "startTime");
            }
        });

        setEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DrunkModeTimePicker();
                ((DrunkModeTimePicker) newFragment).setCurrentContext(getActivity());
                ((DrunkModeTimePicker) newFragment).setCurrentCalendar(chosenEndTime);
                ((DrunkModeTimePicker) newFragment).setCurrentTextView(endTimeText);
                newFragment.show(getFragmentManager(), "endTime");
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateAndTimeForMessage();
            }
        });

    }

    public void setDateAndTimeForMessage() {
        Toast.makeText(getApplicationContext(), "TODO:CONFIRMATION MESSAGE",
                Toast.LENGTH_SHORT).show();

        //For testing purposes only
        if (!getIntent().getBooleanExtra("testingSetTime", false)) {
            this.saveAndFinish();
        }

    }

    public void supersedeChosenStartTimeTextView(TextView startTimeText){
        this.startTimeText = startTimeText;
    }

    public void supersedeChosenTimeEndTextView(TextView endTimeText){
        this.endTimeText = endTimeText;
    }

    public void supersedeChosenStartTime(Calendar chosenStartTime){
        this.chosenStartTime = chosenStartTime;
    }

    public void supersedeChosenEndTime(Calendar chosenEndTime){
        this.chosenEndTime = chosenEndTime;
    }

    public void saveAndFinish() {
        //TODO: Database Addition
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        super.finish();
    }

}
