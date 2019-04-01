package com.fsck.k9.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fsck.k9.DaoSession;
import com.fsck.k9.DrunkMode;
import com.fsck.k9.K9;
import com.fsck.k9.activity.setup.DrunkModeSettings;
import com.fsck.k9.fragment.DrunkModeTimePicker;

import java.util.Calendar;
import java.util.Date;

import com.fsck.k9.R;

public class SetDrunkModeTime extends K9Activity {

    private Button setStarTime;
    private Button setEndTime;
    private Button setTime;

    private TextView startTimeText;
    private TextView endTimeText;

    private Calendar chosenStartTime;
    private Calendar chosenEndTime;

    private DaoSession daoSession;
    private DrunkMode drunkModeSettings;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_drunk_mode_time);

        daoSession = ((K9)getApplication()).getDaoSession();
        drunkModeSettings = daoSession.getDrunkModeDao().loadByRowId(1);

        setStarTime = (Button) findViewById(R.id.drunk_mode_set_start_time_button);
        setEndTime = (Button) findViewById(R.id.drunk_mode_set_end_time_button);
        setTime = (Button) findViewById(R.id.drunk_mode_set_time_button);

        startTimeText = (TextView) findViewById(R.id.drunk_mode_start_time);
        endTimeText = (TextView) findViewById(R.id.drunk_mode_end_time);

        chosenStartTime = Calendar.getInstance();
        chosenStartTime.clear();
        chosenStartTime.setTime(drunkModeSettings.getStartTime());
        chosenEndTime = Calendar.getInstance();
        chosenEndTime.clear();
        chosenEndTime.setTime(drunkModeSettings.getEndTime());

        startTimeText.setText(dateToCalendarFormat(drunkModeSettings.getStartTime()));
        endTimeText.setText(dateToCalendarFormat(drunkModeSettings.getEndTime()));

        setStarTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DrunkModeTimePicker();
                ((DrunkModeTimePicker) newFragment).setCurrentContext(SetDrunkModeTime.this);
                ((DrunkModeTimePicker) newFragment).setCurrentCalendar(chosenStartTime);
                ((DrunkModeTimePicker) newFragment).setCurrentTextView(startTimeText);
                newFragment.show(getFragmentManager(), "startTime");
            }
        });

        setEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DrunkModeTimePicker();
                ((DrunkModeTimePicker) newFragment).setCurrentContext(SetDrunkModeTime.this);
                ((DrunkModeTimePicker) newFragment).setCurrentCalendar(chosenEndTime);
                ((DrunkModeTimePicker) newFragment).setCurrentTextView(endTimeText);
                newFragment.show(getFragmentManager(), "endTime");
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenStartTime.equals(chosenEndTime)){
                    Toast.makeText(getApplicationContext(), "Start time and end time can't be the same!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                confirmSetTime();
            }
        });

    }

    public void confirmSetTime() {
        Toast.makeText(getApplicationContext(), "Time Set!",
                Toast.LENGTH_SHORT).show();

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

    private String dateToCalendarFormat(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(time);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String strTime = (hourOfDay%12) + ":" + ((minute < 10) ? "0" + minute : minute) + (hourOfDay > 12 ? " PM" : " AM");
        return strTime;
    }

    public void saveAndFinish() {
        Date startTime = chosenStartTime.getTime();
        Date endTime = chosenEndTime.getTime();
        drunkModeSettings.setStartTime(startTime);
        drunkModeSettings.setEndTime(endTime);
        daoSession.getDrunkModeDao().update(drunkModeSettings);
        daoSession.clear();

        Intent i = new Intent(getApplicationContext(), DrunkModeSettings.class);
        i.putExtra("refresh needed", true);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}
