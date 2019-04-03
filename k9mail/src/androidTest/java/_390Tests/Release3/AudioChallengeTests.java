package _390Tests.Release3;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.activity.drunk_mode_challenges.AudioChallenge;

import org.junit.Rule;
import org.junit.Test;

public class AudioChallengeTests {

    @Rule
    ActivityTestRule<AudioChallenge> testRule = new ActivityTestRule<AudioChallenge>(AudioChallenge.class);

    @Test
    public void enterCorrectAnswer()
    {

    }

    @Test
    public void enterIncorrectAnswer()
    {

    }
}
