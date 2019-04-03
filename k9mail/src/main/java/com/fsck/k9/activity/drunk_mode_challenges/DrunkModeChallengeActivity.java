package com.fsck.k9.activity.drunk_mode_challenges;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.fsck.k9.R;
import com.fsck.k9.activity.Accounts;
import com.fsck.k9.activity.K9Activity;

/**
 * Abstraction of a drunk mode challenge. A challenge can either result in a win or loss. The user
 * also loses the challenge if they use the Android back button or home button.
 */
public abstract class DrunkModeChallengeActivity extends K9Activity {

    protected MediaPlayer winSound;
    protected MediaPlayer loseSound;
    protected MediaPlayer timeoutSound;

    boolean active = true;
    protected boolean complete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        winSound = MediaPlayer.create(this, R.raw.win_sound);
        loseSound = MediaPlayer.create(this, R.raw.lose_sound);
        timeoutSound = MediaPlayer.create(this, R.raw.timeup_sound);
        complete = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeoutSound.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        active = false;
        if (!complete) {
            loseChallenge();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        active = true;
        if (complete) {
            loseWithDelay(0);
        }
    }

    @Override
    public void onBackPressed() {
        loseChallenge();
    }

    /**
     * Causes the user to be booted to accounts page (unless practice)
     * @param millis amount of time before the activity actually ends
     */
    protected void loseWithDelay(int millis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent failedChallenge = new Intent(getApplicationContext(), Accounts.class);
                failedChallenge.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                if (!active) {
                    return;
                }
                finish();
                if (!getIntent().getBooleanExtra("Practice", false)) {
                    startActivity(failedChallenge);
                }
            }
        }, millis);
    }

    /**
     * This should run and handle the case of the user losing a challenge
     */
    abstract protected void loseChallenge();

    /**
     * This should run and handle the case of the user winning a challenge
     */
    abstract protected void winChallenge();
}
