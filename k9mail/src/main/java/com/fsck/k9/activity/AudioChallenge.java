package com.fsck.k9.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.R;

public class AudioChallenge extends K9Activity {

    private String answer = "";
    private char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3', '4', '5', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                                                '6', '7', '8', '9', '0'};


    private EditText answerInput;
    private Button button_play_sound_again;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_challenge);

        for (int i = 0; i < 6; i++) {
            int random = (int)(Math.random()*chars.length);

            answer += chars[random];
        }

        button_play_sound_again = findViewById(R.id.button_play_sound_again);
        button_play_sound_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
            }
        });

        playSound();
    }

    private void playSound()
    {
        final String answer = this.answer;
    }
}
