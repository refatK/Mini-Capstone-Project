package _390Tests.Release3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.fsck.k9.R;
import com.fsck.k9.activity.drunk_mode_challenges.PhotoChallenge;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasBackground;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

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
        pc.putExtras(testPack);
        photoC.launchActivity(pc);
        setFields(photoC.getActivity());
    }

    private void setFields(PhotoChallenge activity) {
        prompt = activity.findViewById(R.id.prompt);
        choice1 = activity.findViewById(R.id.choice1);
        choice2 = activity.findViewById(R.id.choice2);
        choice3 = activity.findViewById(R.id.choice3);
        choice4 = activity.findViewById(R.id.choice4);
        challengePhoto = activity.findViewById(R.id.imageView);
    }

    @Test
    public void testChooseFirstWrongAnswer() {
        Espresso.onView(withId(challengePhoto.getId())).check(matches(isDisplayed()));
        Espresso.onView(withId(choice1.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice2.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice3.getId())).check(matches(isClickable()));
        Espresso.onView(withId(choice4.getId())).check(matches(isClickable()));
        Espresso.onView(withText("Wolf")).perform(click());
        Espresso.onView(withId(choice1.getId())).check(matches(withText("Wolf ❌")));
        Espresso.onView(withId(choice2.getId())).check(matches(withText("Fox")));
        Espresso.onView(withId(choice3.getId())).check(matches(withText("Dog")));
        Espresso.onView(withId(choice4.getId())).check(matches(withText("Coyote")));
        Espresso.onView(withId(prompt.getId())).check(matches(withText(R.string.photo_challenge_failed)));
        Espresso.onView(withId(prompt.getId())).check(matches(hasTextColor(android.R.color.white)));
        Espresso.onView(withId(choice1.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.white), withId(choice1.getId())));
        Espresso.onView(withId(choice2.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice2.getId())));
        Espresso.onView(withId(choice3.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice3.getId())));
        Espresso.onView(withId(choice4.getId())).check(selectedDescendantsMatch(hasTextColor(android.R.color.black), withId(choice4.getId())));
    }
}