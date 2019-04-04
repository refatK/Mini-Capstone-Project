package com.fsck.k9.activity.drunk_mode_challenges;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsck.k9.K9;
import com.fsck.k9.Photo;
import com.fsck.k9.R;

import java.util.List;


public class PhotoChallenge extends DrunkModeChallengeActivity {
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private ImageView mysteryPicture;
    private Photo challengePhoto;
    private TextView prompt;
    private Handler timeLimit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_challenge);

        prompt = findViewById(R.id.prompt);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(getIntent().getBooleanExtra("test", false)) {
                    pickTestChallengePhoto();
                }
                else {
                    pickChallengePhoto();
                }
            }
        }).run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setChoices();
                setListeners(choice1, choice2, choice3, choice4);
            }
        }).run();
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
    protected void pickTestChallengePhoto() {
        challengePhoto = new Photo(null, "drawable/dog",
                "Dog", "Wolf", "Fox", "Dog", "Coyote");
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
        prompt.setText(R.string.drunk_mode_challenge_failed);
        mysteryPicture.setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
        loseWithDelay(500);
    }

    @Override
    protected void loseChallenge() {
        complete = true;
        prompt.setBackgroundColor(Color.RED);
        prompt.setTextColor(Color.WHITE);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        prompt.setText(R.string.drunk_mode_challenge_failed);
        mysteryPicture.setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
        loseWithDelay(0);
    }

    @Override
    protected void winChallenge() {
        complete = true;
        winSound.start();
        prompt.setBackgroundColor(Color.GREEN);
        prompt.setTextColor(Color.BLACK);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        prompt.setText(R.string.drunk_mode_challenge_success);
        mysteryPicture.setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);

    }

    private void winChallenge(Button choice) {
        choice.setBackgroundColor(Color.GREEN);
        choice.setTextColor(Color.BLACK);
        //This value is set by the DB, it's not possible to use the strings.xml
        choice.setText(choice.getText()+" ✔");

        winChallenge();
    }

    private void timeOut(){
        if(complete) {
            return;
        }
        complete = true;
        timeoutSound.start();
        prompt.setBackgroundColor(Color.YELLOW);
        prompt.setTextColor(Color.BLACK);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        prompt.setText(getString(R.string.drunk_mode_challenge_timeout, 10));
        mysteryPicture.setColorFilter(Color.YELLOW, PorterDuff.Mode.DARKEN);
        loseWithDelay(500);
    }

}
