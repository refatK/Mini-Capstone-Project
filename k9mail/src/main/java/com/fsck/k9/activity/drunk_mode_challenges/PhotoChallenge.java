package com.fsck.k9.activity.drunk_mode_challenges;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fsck.k9.K9;
import com.fsck.k9.Photo;
import com.fsck.k9.R;
import com.fsck.k9.activity.K9Activity;


public class PhotoChallenge extends K9Activity {
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private ImageView mysteryPicture;
    private Photo challengePhoto;
    private String guess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_challenge);
        challengePhoto = K9.daoSession.getPhotoDao().load(1L);
        mysteryPicture = findViewById(R.id.imageView);
        setChoices();
        int challengePhotoID = getResources().getIdentifier(challengePhoto.getFileName(), null, this.getPackageName());
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
}
