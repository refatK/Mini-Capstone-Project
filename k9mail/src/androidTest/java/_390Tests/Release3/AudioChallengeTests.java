package _390Tests.Release3;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.fsck.k9.R;
import com.fsck.k9.activity.drunk_mode_challenges.AudioChallenge;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AudioChallengeTests {

    @Rule
    public ActivityTestRule<AudioChallenge> testRule = new ActivityTestRule<AudioChallenge>(AudioChallenge.class);

    @Test
    public void enterCorrectAnswer()
    {
        String input = testRule.getActivity().getAnswer();

        assertEquals(input, testRule.getActivity().getAnswer());

        while(testRule.getActivity().isPlaying()) {

        }

        Espresso.onView(withId(R.id.audio_challenge_input)).perform(click());
        Espresso.onView(withId(R.id.audio_challenge_input)).perform(typeTextIntoFocusedView(input));
        Espresso.onView(withId(R.id.button_sound_ok)).perform(click());

        assertTrue(testRule.getActivity().getWin());
        fail();
    }

    @Test
    public void enterIncorrectAnswer()
    {
        String input = "abc12345";

        assertNotEquals(input, testRule.getActivity().getAnswer());


        while(testRule.getActivity().isPlaying()) {

        }

        Espresso.onView(withId(R.id.audio_challenge_input)).perform(click());
        Espresso.onView(withId(R.id.audio_challenge_input)).perform(typeTextIntoFocusedView(input));
        Espresso.onView(withId(R.id.button_sound_ok)).perform(click());

        assertTrue(testRule.getActivity().getLose());
    }
}
