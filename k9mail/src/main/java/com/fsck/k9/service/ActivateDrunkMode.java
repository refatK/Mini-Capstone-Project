package com.fsck.k9.service;

import com.fsck.k9.DrunkMode;
import com.fsck.k9.K9;
import com.fsck.k9.DaoSession;
import com.fsck.k9.activity.drunk_mode_challenges.AudioChallenge;
import com.fsck.k9.activity.drunk_mode_challenges.PhotoChallenge;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.app.IntentService;
import android.content.Intent;

public class ActivateDrunkMode extends IntentService {

    private DaoSession daoSession;
    private DrunkMode drunkModeSettings;

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

        Date currentTimeDate = Calendar.getInstance().getTime();
        int currentTime =currentTimeDate.getHours()*60+currentTimeDate.getMinutes();
        int startTime = drunkModeSettings.getStartTime().getHours()*60+drunkModeSettings.getStartTime().getMinutes();
        int endTime = drunkModeSettings.getEndTime().getHours()*60+drunkModeSettings.getEndTime().getMinutes();

        if(drunkModeSettings.getIsDrunk() && startTime <= currentTime && currentTime < endTime){
            final Class<?>[] activities = {AudioChallenge.class, PhotoChallenge.class};
            int random = new Random().nextInt(2);
            Intent intent= new Intent(this, activities[random]);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
