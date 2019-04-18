package _390Tests.Release3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.fsck.k9.R;
import com.fsck.k9.activity.drunk_mode_challenges.PhotoChallenge;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;

public class PhotoChallengeTestUI {
    //Tests that the challenge behaves correctly based on what's being displayed
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

    private boolean checkViewColor(View v, int color) {
        ColorDrawable background =(ColorDrawable) v.getBackground();
        return background.getColor() == color;
    }

    @Test
    public void testChooseFirstWrongAnswer() {
        onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));

        assertTrue(checkViewColor(prompt,Color.DKGRAY));

        onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        onView(withId(choice1.getId())).check(matches(isClickable()));
        onView(withId(choice2.getId())).check(matches(isClickable()));
        onView(withId(choice3.getId())).check(matches(isClickable()));
        onView(withId(choice4.getId())).check(matches(isClickable()));
        onView(withText("Wolf")).perform(click());
        onView(withId(choice1.getId())).check(matches(withText("Wolf ❌")));
        onView(withId(choice2.getId())).check(matches(withText("Fox")));
        onView(withId(choice3.getId())).check(matches(withText("Dog")));
        onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_failed)));

        assertTrue(checkViewColor(prompt, Color.RED));
        assertTrue(checkViewColor(choice1, Color.RED));
        assertTrue(photoC.getActivity().loseSoundPlaying());
        assertFalse(photoC.getActivity().timeoutSoundPlaying());
        assertFalse(photoC.getActivity().winSoundPlaying());

        onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice1.getId())));
        onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));
    }

    @Test
    public void testChooseSecondWrongAnswer(){
        onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));

        assertTrue(checkViewColor(prompt,Color.DKGRAY));

        onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        onView(withId(choice1.getId())).check(matches(isClickable()));
        onView(withId(choice2.getId())).check(matches(isClickable()));
        onView(withId(choice3.getId())).check(matches(isClickable()));
        onView(withId(choice4.getId())).check(matches(isClickable()));
        onView(withText("Fox")).perform(click());
        onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        onView(withId(choice2.getId())).check(matches(withText("Fox ❌")));
        onView(withId(choice3.getId())).check(matches(withText("Dog")));
        onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_failed)));

        assertTrue(checkViewColor(prompt, Color.RED));
        assertTrue(checkViewColor(choice2, Color.RED));
        assertTrue(photoC.getActivity().loseSoundPlaying());
        assertFalse(photoC.getActivity().timeoutSoundPlaying());
        assertFalse(photoC.getActivity().winSoundPlaying());

        onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice2.getId())));
        onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));

    }

    @Test
    public void testChooseThirdWrongAnswer(){
        onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));

        assertTrue(checkViewColor(prompt,Color.DKGRAY));

        onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        onView(withId(choice1.getId())).check(matches(isClickable()));
        onView(withId(choice2.getId())).check(matches(isClickable()));
        onView(withId(choice3.getId())).check(matches(isClickable()));
        onView(withId(choice4.getId())).check(matches(isClickable()));
        onView(withText("Coyote")).perform(click());
        onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        onView(withId(choice2.getId())).check(matches(withText("Fox")));
        onView(withId(choice3.getId())).check(matches(withText("Dog")));
        onView(withId(choice4.getId())).check(matches(withText("Coyote ❌")));
        onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_failed)));

        assertTrue(checkViewColor(prompt, Color.RED));
        assertTrue(checkViewColor(choice4, Color.RED));
        assertTrue(photoC.getActivity().loseSoundPlaying());
        assertFalse(photoC.getActivity().timeoutSoundPlaying());
        assertFalse(photoC.getActivity().winSoundPlaying());

        onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice4.getId())));

    }

    @Test
    public void testChooseCorrectAnswer(){
        onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));

        assertTrue(checkViewColor(prompt,Color.DKGRAY));

        onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        onView(withId(choice1.getId())).check(matches(isClickable()));
        onView(withId(choice2.getId())).check(matches(isClickable()));
        onView(withId(choice3.getId())).check(matches(isClickable()));
        onView(withId(choice4.getId())).check(matches(isClickable()));
        onView(withText("Dog")).perform(click());
        onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        onView(withId(choice2.getId())).check(matches(withText("Fox")));
        onView(withId(choice3.getId())).check(matches(withText("Dog ✔")));
        onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        onView(withId(prompt.getId())).check(matches(withText(R.string.drunk_mode_challenge_success)));

        assertTrue(checkViewColor(prompt, Color.GREEN));
        assertTrue(checkViewColor(choice3, Color.GREEN));
        assertTrue(photoC.getActivity().winSoundPlaying());
        assertFalse(photoC.getActivity().timeoutSoundPlaying());
        assertFalse(photoC.getActivity().loseSoundPlaying());

        onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.black)));
        onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));

    }
    @Test
    public void testTimeout() {
        onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));

        assertTrue(checkViewColor(prompt,Color.DKGRAY));

        onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_prompt)));
        onView(withId(choice1.getId())).check(matches(isClickable()));
        onView(withId(choice2.getId())).check(matches(isClickable()));
        onView(withId(choice3.getId())).check(matches(isClickable()));
        onView(withId(choice4.getId())).check(matches(isClickable()));

        onView(withId(choice1.getId())).check(matches(withText("Wolf")));
        onView(withId(choice2.getId())).check(matches(withText("Fox")));
        onView(withId(choice3.getId())).check(matches(withText("Dog")));
        onView(withId(choice4.getId())).check(matches(withText("Coyote")));

        SystemClock.sleep(10000);
        onView(withId(prompt.getId())).check(matches(withText(timeoutString)));
        onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.black)));

        assertTrue(checkViewColor(prompt, Color.YELLOW));
        assertTrue(photoC.getActivity().timeoutSoundPlaying());
        assertFalse(photoC.getActivity().loseSoundPlaying());
        assertFalse(photoC.getActivity().winSoundPlaying());

        onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice1.getId())));
        onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));
    }
}