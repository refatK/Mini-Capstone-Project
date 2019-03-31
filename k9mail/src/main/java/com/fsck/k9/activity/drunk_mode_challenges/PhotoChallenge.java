package com.fsck.k9.activity.drunk_mode_challenges;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsck.k9.R;
import com.fsck.k9.activity.K9Activity;

public class PhotoChallenge extends K9Activity {
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private ImageView mysteryPicture;
    private String answer;
    private String guess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_challenge);
    }

}
