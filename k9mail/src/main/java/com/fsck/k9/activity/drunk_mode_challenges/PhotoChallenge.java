package com.fsck.k9.activity.drunk_mode_challenges;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
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
    private boolean complete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_challenge);
        complete = false;
        prompt = findViewById(R.id.prompt);
        pickChallengePhoto();
        setChoices();
        setListenters(choice1, choice2, choice3, choice4);
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

    private void setListenters(Button... choices){
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
        choice.setBackgroundColor(Color.RED);
        choice.setTextColor(Color.WHITE);
        choice.setText(choice.getText()+"❌");

        prompt.setBackgroundColor(Color.RED);
        prompt.setTextColor(Color.WHITE);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        prompt.setText("Sorry, Challenge Failed! 👎");
        mysteryPicture.setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);

        Intent failedChallenge = new Intent(getApplicationContext(), FolderList.class);
        finish();
        startActivity(failedChallenge);
    }

    private void winChallenge(Button choice) {
        complete = true;
        choice.setBackgroundColor(Color.GREEN);
        choice.setTextColor(Color.BLACK);
        choice.setText(choice.getText()+" ✔");
        prompt.setBackgroundColor(Color.GREEN);
        prompt.setTextColor(Color.BLACK);
        prompt.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        prompt.setText("Congrats, Challenge Completed! 👍");
        mysteryPicture.setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);
        finish();
    }
}
