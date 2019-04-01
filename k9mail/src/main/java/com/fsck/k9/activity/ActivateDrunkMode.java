package com.fsck.k9.activity;

//import android.content.Intent;
import com.fsck.k9.DrunkMode;
import com.fsck.k9.K9;
import com.fsck.k9.DaoSession;
import com.fsck.k9.R;

import java.util.Calendar;
import java.util.Date;

import android.media.MediaPlayer;

public class ActivateDrunkMode extends K9PreferenceActivity {

    private DaoSession daoSession;
    private DrunkMode drunkModeSettings;
    private MediaPlayer loseSound= MediaPlayer.create(this, R.raw.lose_sound);

    public ActivateDrunkMode() {
    }

    public void checkDrunkMode() {
        daoSession = ((K9)getApplication()).getDaoSession();
        drunkModeSettings = daoSession.getDrunkModeDao().loadByRowId(1);

        Date currentTimeDate = Calendar.getInstance().getTime();
        int currentTime =currentTimeDate.getHours()*60+currentTimeDate.getMinutes();
        int startTime = drunkModeSettings.getStartTime().getHours()*60+drunkModeSettings.getStartTime().getMinutes();
        int endTime = drunkModeSettings.getEndTime().getHours()*60+drunkModeSettings.getEndTime().getMinutes();

        if(startTime <= currentTime && currentTime < endTime){
        //    Intent drunk = new Intent(getApplicationContext(), FolderList.class);
        //    startActivity(drunk);
            loseSound.start();
        }
    }
}
