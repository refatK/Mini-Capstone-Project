package _390Tests.Release3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.fsck.k9.R;
import com.fsck.k9.activity.drunk_mode_challenges.PhotoChallenge;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PhotoChallengeTestUI {
    /* Tests that the challenge behaves correctly based on what's being displayed*/
    @Rule
    public ActivityTestRule<PhotoChallenge> photoC =
            new ActivityTestRule<PhotoChallenge>
                    (PhotoChallenge.class, true, false);
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private TextView prompt;
    private ImageView challengePhoto;
    private String timeoutString;

    @Before
    public void setup() {
        Intent pc = new Intent();
        Bundle testPack = new Bundle();
        testPack.putBoolean("test", true);
        testPack.putBoolean("Practice", true);
        pc.putExtras(testPack);
        photoC.launchActivity(pc);
        setFields(photoC.getActivity());
    }

    @After
    public void teardown() {
        photoC.finishActivity();
    }

    private void setFields(PhotoChallenge activity) {
        prompt = activity.findViewById(R.id.prompt);
        choice1 = activity.findViewById(R.id.choice1);
        choice2 = activity.findViewById(R.id.choice2);
        choice3 = activity.findViewById(R.id.choice3);
        choice4 = activity.findViewById(R.id.choice4);
        challengePhoto = activity.findViewById(R.id.imageView);
        timeoutString = activity
                .getApplicationContext()
                    .getString(R.string.drunk_mode_challenge_timeout, 10);
    }

    @Test
    public void testChooseFirstWrongAnswer() {
        Espresso.onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        Espresso.onView(withId(choice1.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice2.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice3.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice4.getId())).check(matches(isClickable()));
        Espresso.onView(withText("Wolf")).perform(click());
        Espresso.onView(withId(choice1.getId())).check(matches(withText("Wolf ❌")));
        Espresso.onView(withId(choice2.getId())).check(matches(withText("Fox")));
        Espresso.onView(withId(choice3.getId())).check(matches(withText("Dog")));
        Espresso.onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_failed)));

        assertTrue(photoC.getActivity().checkViewColor(prompt, Color.RED));
        assertTrue(photoC.getActivity().checkViewColor(choice1, Color.RED));
        assertTrue(photoC.getActivity().loseSoundPlaying());

        Espresso.onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        Espresso.onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice1.getId())));
        Espresso.onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        Espresso.onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        Espresso.onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));
    }

    @Test
    public void testChooseSecondWrongAnswer(){
        Espresso.onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        Espresso.onView(withId(choice1.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice2.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice3.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice4.getId())).check(matches(isClickable()));
        Espresso.onView(withText("Fox")).perform(click());
        Espresso.onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        Espresso.onView(withId(choice2.getId())).check(matches(withText("Fox ❌")));
        Espresso.onView(withId(choice3.getId())).check(matches(withText("Dog")));
        Espresso.onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_failed)));

        assertTrue(photoC.getActivity().checkViewColor(prompt, Color.RED));
        assertTrue(photoC.getActivity().checkViewColor(choice2, Color.RED));
        assertTrue(photoC.getActivity().loseSoundPlaying());

        Espresso.onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        Espresso.onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        Espresso.onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice2.getId())));
        Espresso.onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        Espresso.onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));

    }

    @Test
    public void testChooseThirdWrongAnswer(){
        Espresso.onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        Espresso.onView(withId(choice1.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice2.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice3.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice4.getId())).check(matches(isClickable()));
        Espresso.onView(withText("Coyote")).perform(click());
        Espresso.onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        Espresso.onView(withId(choice2.getId())).check(matches(withText("Fox")));
        Espresso.onView(withId(choice3.getId())).check(matches(withText("Dog")));
        Espresso.onView(withId(choice4.getId())).check(matches(withText("Coyote ❌")));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_failed)));

        assertTrue(photoC.getActivity().checkViewColor(prompt, Color.RED));
        assertTrue(photoC.getActivity().checkViewColor(choice4, Color.RED));
        assertTrue(photoC.getActivity().loseSoundPlaying());

        Espresso.onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        Espresso.onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        Espresso.onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        Espresso.onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        Espresso.onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice4.getId())));

    }

    @Test
    public void testChooseCorrectAnswer(){
        Espresso.onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        Espresso.onView(withId(choice1.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice2.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice3.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice4.getId())).check(matches(isClickable()));
        Espresso.onView(withText("Dog")).perform(click());
        Espresso.onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        Espresso.onView(withId(choice2.getId())).check(matches(withText("Fox")));
        Espresso.onView(withId(choice3.getId())).check(matches(withText("Dog ✔")));
        Espresso.onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_success)));

        assertTrue(photoC.getActivity().checkViewColor(prompt, Color.GREEN));
        assertTrue(photoC.getActivity().checkViewColor(choice3, Color.GREEN));
        assertTrue(photoC.getActivity().winSoundPlaying());

        Espresso.onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.black)));
        Espresso.onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        Espresso.onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        Espresso.onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        Espresso.onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));

    }
    @Test
    public void testTimeout() {
        Espresso.onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        Espresso.onView(withId(choice1.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice2.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice3.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice4.getId())).check(matches(isClickable()));

        SystemClock.sleep(10000);

        Espresso.onView(withId(prompt.getId())).check(matches(withText(timeoutString)));
        Espresso.onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.black)));

        assertTrue(photoC.getActivity().checkViewColor(prompt, Color.YELLOW));
        assertTrue(photoC.getActivity().timeoutSoundPlaying());

        Espresso.onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        Espresso.onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        Espresso.onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        Espresso.onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));
    }
}