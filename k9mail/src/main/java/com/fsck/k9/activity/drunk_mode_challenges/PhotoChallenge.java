package com.fsck.k9.activity.drunk_mode_challenges;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsck.k9.K9;
import com.fsck.k9.Photo;
import com.fsck.k9.R;
import com.fsck.k9.activity.FolderList;
import com.fsck.k9.activity.K9Activity;

import java.util.List;


public class PhotoChallenge extends K9Activity {
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private ImageView mysteryPicture;
    private Photo challengePhoto;
    private TextView prompt;
    private MediaPlayer winSound;
    private MediaPlayer loseSound;
    private MediaPlayer timeoutSound;
    private boolean complete;
    private Handler timeLimit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_challenge);
        winSound = MediaPlayer.create(this, R.raw.win_sound);
        loseSound = MediaPlayer.create(this, R.raw.lose_sound);
        timeoutSound = MediaPlayer.create(this, R.raw.timeup_sound);
        complete = false;
        prompt = findViewById(R.id.prompt);
        pickChallengePhoto();
        setChoices();
        setListeners(choice1, choice2, choice3, choice4);
        timeLimit = new Handler();
        timeLimit.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeOut();
            }
        },10000);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        timeLimit.removeCallbacksAndMessages(null);
        timeoutSound.stop();
    }

    private void pickChallengePhoto() {
        List<Photo> allPossiblePhotos = K9.daoSession.getPhotoDao().loadAll();
        //gets a random photo from all the possibilities
        challengePhoto = allPossiblePhotos
                .get((int)(Math.random()*((allPossiblePhotos.size()))));
        mysteryPicture = findViewById(R.id.imageView);

        int challengePhotoID = getResources()
                .getIdentifier(challengePhoto.getFileName(), null, this.getPackageName());

        mysteryPicture.setImageResource(challengePhotoID);
    }

    private void setChoices() {
        choice1 = findViewById(R.id.choice1);
        choice1.setText(challengePhoto.getChoice1());
        choice2 = findViewById(R.id.choice2);
        choice2.setText(challengePhoto.getChoice2());
        choice3 = findViewById(R.id.choice3);
        choice3.setText(challengePhoto.getChoice3());
        choice4 = findViewById(R.id.choice4);
        choice4.setText(challengePhoto.getChoice4());
    }

    private void setListeners(Button... choices){
        for(final Button choice : choices){
            choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(complete) {
                        return;
                    }
                    checkSelection(choice);
                }
            });
        }
    }

    private void checkSelection(Button choice) {
        if(choice.getText().equals(challengePhoto.getAnswer())){
            winChallenge(choice);
        }
        else{
            loseChallenge(choice);
        }
    }

    private void loseChallenge(Button choice) {
        complete = true;
        loseSound.start();
        choice.setBackgroundColor(Color.RED);
        choice.setTextColor(Color.WHITE);
        //This value is set by the DB, it's not possible to use the strings.xml
        choice.setText(choice.getText()+" ❌");

        prompt.setBackgroundColor(Color.RED);
        prompt.setTextColor(Color.WHITE);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        prompt.setText(R.string.photo_challenge_failed);
        mysteryPicture.setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);

        Intent failedChallenge = new Intent(getApplicationContext(), FolderList.class);
        finish();
        if(!getIntent().getBooleanExtra("Practice", false)) {
            startActivity(failedChallenge);
        }
    }

    private void winChallenge(Button choice) {
        complete = true;
        winSound.start();
        choice.setBackgroundColor(Color.GREEN);
        choice.setTextColor(Color.BLACK);
        //This value is set by the DB, it's not possible to use the strings.xml
        choice.setText(choice.getText()+" ✔");
        prompt.setBackgroundColor(Color.GREEN);
        prompt.setTextColor(Color.BLACK);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        prompt.setText(R.string.photo_challenge_success);
        mysteryPicture.setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);
        finish();
    }

    private void timeOut(){
        complete = true;
        timeoutSound.start();
        prompt.setBackgroundColor(Color.YELLOW);
        prompt.setTextColor(Color.BLACK);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        prompt.setText(R.string.photo_challenge_timeout);
        mysteryPicture.setColorFilter(Color.YELLOW, PorterDuff.Mode.DARKEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent failedChallenge = new Intent(getApplicationContext(), FolderList.class);
                finish();
                if(!getIntent().getBooleanExtra("Practice", false)) {
                    startActivity(failedChallenge);
                }
            }
        },500);
    }
}
