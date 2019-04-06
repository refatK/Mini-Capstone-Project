package com.fsck.k9.activity.drunk_mode_challenges;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.R;
import com.fsck.k9.activity.Accounts;

import org.jetbrains.annotations.Nullable;

public class AudioChallenge extends DrunkModeChallengeActivity {

    private String answer = "";
    private char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3', '4', '5', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                                                '6', '7', '8', '9', '0' };


    private EditText answerInput;
    private Button button_play_sound_again;
    private Button button_sound_ok;
    private final MediaPlayer[] mediaPlayers = new MediaPlayer[7];
    private boolean playing = false;
    private boolean win = false;
    private boolean lose = false;
    private TextView audioChallengeText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_challenge);
        audioChallengeText = findViewById(R.id.audio_challenge_text);

        super.timeoutSound.release();
        super.timeoutSound = null;

        for (int i = 0; i < 7; i++) {
            int random = (int)(Math.random()*chars.length);

            answer += chars[random];
        }

        button_sound_ok = findViewById(R.id.button_sound_ok);
        button_sound_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerInput = findViewById(R.id.audio_challenge_input);

                if (answer.equalsIgnoreCase(answerInput.getText().toString())) {
                    win = true;
                    audioChallengeText.setBackgroundColor(Color.parseColor("#228B22"));
                    audioChallengeText.setTextColor(Color.WHITE);
                    winChallenge();
                }
                else {
                    lose = true;
                    audioChallengeText.setBackgroundColor(Color.RED);
                    audioChallengeText.setTextColor(Color.WHITE);
                    loseChallenge();
                }

            }
        });

        button_play_sound_again = findViewById(R.id.button_play_sound_again);
        button_play_sound_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
            }
        });

        playSound();
    }


    @Override
    public void onDestroy() {

        for (MediaPlayer mediaPlayer:mediaPlayers)
        {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
            catch(IllegalStateException e) {
                continue;
            }
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!isComplete())
            loseChallenge();
    }

    @Override
    public void onPause() {
        if (!isComplete())
            loseChallenge();
        super.onPause();
    }

    @Override
    protected void winChallenge() {
        final MediaPlayer winner = super.winSound;
        winner.start();
        winner.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                winner.stop();
                winner.release();
            }
        });
        finish();
    }

    @Override
    protected void loseChallenge()
    {
        if (!isComplete())
            youLoseNoSound();

        else if (isComplete() && (lose && !win))
            youLose();
    }

    private void youLose() {
        final MediaPlayer loser = super.loseSound;
        loser.start();
        loser.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                loser.stop();
                loser.release();
            }
        });
        Intent i = new Intent(getApplicationContext(), Accounts.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        if(!getIntent().getBooleanExtra("Practice", false)) {
            startActivity(i);
        }
    }

    private void youLoseNoSound() {
        Intent i = new Intent(getApplicationContext(), Accounts.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        if(!getIntent().getBooleanExtra("Practice", false)) {
            startActivity(i);
        }
    }

    private void playSound() {

        final String answer = this.answer;

        for (int i = 0; i < 7; i++) {
            mediaPlayers[i] = getMediaPlayer(answer.charAt(i));
        }

        if (!playing) {
            playing = true;

            button_play_sound_again.setClickable(false);
            button_play_sound_again.setEnabled(false);
            button_sound_ok.setClickable(false);
            button_sound_ok.setEnabled(false);

            mediaPlayers[0].start();
            mediaPlayers[0].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[0].stop();
                    mediaPlayers[0].release();
                    mediaPlayers[1].start();
                }
            });

            mediaPlayers[1].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[1].stop();
                    mediaPlayers[1].release();
                    mediaPlayers[2].start();
                }
            });

            mediaPlayers[2].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[2].stop();
                    mediaPlayers[2].release();
                    mediaPlayers[3].start();
                }
            });

            mediaPlayers[3].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[3].stop();
                    mediaPlayers[3].release();
                    mediaPlayers[4].start();
                }
            });

            mediaPlayers[4].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[4].stop();
                    mediaPlayers[4].release();
                    mediaPlayers[5].start();
                }
            });

            mediaPlayers[5].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[5].stop();
                    mediaPlayers[5].release();
                    mediaPlayers[6].start();
                }
            });

            mediaPlayers[6].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayers[6].stop();
                    mediaPlayers[6].release();
                    button_play_sound_again.setClickable(true);
                    button_play_sound_again.setEnabled(true);
                    button_sound_ok.setClickable(true);
                    button_sound_ok.setEnabled(true);
                    playing = false;
                }
            });
        }
    }

    @Nullable
    private MediaPlayer getMediaPlayer(char ch) {
        switch (ch) {
            case '0': return MediaPlayer.create(this, R.raw.n0);
            case '1': return MediaPlayer.create(this, R.raw.n1);
            case '2': return MediaPlayer.create(this, R.raw.n2);
            case '3': return MediaPlayer.create(this, R.raw.n3);
            case '4': return MediaPlayer.create(this, R.raw.n4);
            case '5': return MediaPlayer.create(this, R.raw.n5);
            case '6': return MediaPlayer.create(this, R.raw.n6);
            case '7': return MediaPlayer.create(this, R.raw.n7);
            case '8': return MediaPlayer.create(this, R.raw.n8);
            case '9': return MediaPlayer.create(this, R.raw.n9);

            case 'a': return MediaPlayer.create(this, R.raw.a);
            case 'b': return MediaPlayer.create(this, R.raw.b);
            case 'c': return MediaPlayer.create(this, R.raw.c);
            case 'd': return MediaPlayer.create(this, R.raw.d);
            case 'e': return MediaPlayer.create(this, R.raw.e);
            case 'f': return MediaPlayer.create(this, R.raw.f);
            case 'g': return MediaPlayer.create(this, R.raw.g);
            case 'h': return MediaPlayer.create(this, R.raw.h);
            case 'i': return MediaPlayer.create(this, R.raw.i);
            case 'j': return MediaPlayer.create(this, R.raw.j);
            case 'k': return MediaPlayer.create(this, R.raw.k);
            case 'l': return MediaPlayer.create(this, R.raw.l);
            case 'm': return MediaPlayer.create(this, R.raw.m);
            case 'n': return MediaPlayer.create(this, R.raw.n);
            case 'o': return MediaPlayer.create(this, R.raw.o);
            case 'p': return MediaPlayer.create(this, R.raw.p);
            case 'q': return MediaPlayer.create(this, R.raw.q);
            case 'r': return MediaPlayer.create(this, R.raw.r);
            case 's': return MediaPlayer.create(this, R.raw.s);
            case 't': return MediaPlayer.create(this, R.raw.t);
            case 'u': return MediaPlayer.create(this, R.raw.u);
            case 'v': return MediaPlayer.create(this, R.raw.v);
            case 'w': return MediaPlayer.create(this, R.raw.w);
            case 'x': return MediaPlayer.create(this, R.raw.x);
            case 'y': return MediaPlayer.create(this, R.raw.y);
            case 'z': return MediaPlayer.create(this, R.raw.z);
        }

        return null;
    }


    private boolean isComplete() {
        super.complete = (win || lose);
        return super.complete;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public String getAnswer() {
        return this.answer;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public boolean isPlaying() {
        return this.playing;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public boolean getWin() {
        return this.win;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public boolean getLose() {
        return this.lose;
    }
}
