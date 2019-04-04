package _390Tests.Release3;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.fsck.k9.R;
import com.fsck.k9.activity.drunk_mode_challenges.PhotoChallenge;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class PhotoChallengeTestUI {
    /* Tests that the challenge behaves correctly based on what's being displayed*/
    @Rule
    public ActivityTestRule<PhotoChallenge> photoC =
            new ActivityTestRule<PhotoChallenge>
                    (PhotoChallenge.class, false, false);
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private TextView prompt;
    private ImageView challengePhoto;

    @Before
    public void setup() {
        Intent pc = new Intent();
        Bundle testPack = new Bundle();
        testPack.putBoolean("test", true);
        testPack.putBoolean("Practice", true);
        pc.putExtras(testPack);
        photoC.launchActivity(pc);
    }

    @Test
    public void testChooseWrongAnswer() {

    }
}