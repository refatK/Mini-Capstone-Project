package com.fsck.k9.service;

import com.fsck.k9.DrunkMode;
import com.fsck.k9.K9;
import com.fsck.k9.DaoSession;
import com.fsck.k9.activity.drunk_mode_challenges.AudioChallenge;
import com.fsck.k9.activity.drunk_mode_challenges.MathChallenge;
import com.fsck.k9.activity.drunk_mode_challenges.PhotoChallenge;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.IntentService;
import android.content.Intent;

public class ActivateDrunkMode extends IntentService {

    private DaoSession daoSession;
    private DrunkMode drunkModeSettings;

    public final int MIDNIGHT = 1440;
    public final int INVALID_TIME = 8000;
    //The amount of minutes in a day does not exceed 2880; 8000 is a fallback value
    public int currentTime = INVALID_TIME;
    public int startTime = INVALID_TIME;
    public int endTime = INVALID_TIME;
    public boolean drunkModeEnabled;
    public boolean isTest;
    public final Class<?>[] drunkModeChallenges = {
            PhotoChallenge.class,
            AudioChallenge.class,
            MathChallenge.class
    };

    public ActivateDrunkMode(){
        super("Drunk Mode");
    }

    @Override
    public void onHandleIntent(Intent i) {
        Thread drunkMode = new Thread(new Runnable() {
            @Override
            public void run() {
                startDrunkMode();
            }
        });
        drunkMode.run();

    }
    private void startDrunkMode(){
        daoSession = ((K9)getApplication()).getDaoSession();
        drunkModeSettings = daoSession.getDrunkModeDao().loadByRowId(1);

        if(isEnabled() && isItGoTime()) {
            int random = new Random().nextInt(drunkModeChallenges.length);
            Intent intent= new Intent(this, drunkModeChallenges[random]);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public boolean isEnabled(){
        if(!isTest){
            this.drunkModeEnabled=drunkModeSettings.getIsDrunk();
            return drunkModeEnabled;
        }
        else{
            return drunkModeEnabled;
        }
    }

    public void setDrunkMode(boolean boo){
        this.drunkModeEnabled = boo;
    }

    public void enableTest(){
        this.isTest = true;
    }

    public boolean isItGoTime(){
        boolean goTime=false;
        Date currentTimeDate = Calendar.getInstance().getTime();

        if(isItNotTestTime()) {
            this.currentTime=(currentTimeDate.getHours()*60+currentTimeDate.getMinutes());
            this.startTime=(drunkModeSettings.getStartTime().getHours()*60+drunkModeSettings.getStartTime().getMinutes());
            this.endTime=(drunkModeSettings.getEndTime().getHours()*60+drunkModeSettings.getEndTime().getMinutes());
        }

        if (startTime > endTime) {
            if ((startTime <= currentTime && currentTime < MIDNIGHT) || (currentTime < endTime)){
                goTime=true;
            }
        }
        else if (startTime <= currentTime && currentTime < endTime){
            goTime=true;
        }
        return goTime;
    }

    public boolean isItNotTestTime(){
        return (currentTime==INVALID_TIME || startTime==INVALID_TIME || endTime==INVALID_TIME);
        //If a test is running, it will set custom times, otherwise this is true
        // and must grab real times
    }

    public void setCurrentTime(int t){
        this.currentTime = t;
    }

    public void setStartTime(int t){
        this.startTime = t;
    }

    public void setEndTime(int t){
        this.endTime = t;
    }

}
