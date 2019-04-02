package com.fsck.k9.activity.drunk_mode_challenges;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.fsck.k9.activity.K9Activity;
import com.fsck.k9.R;

public class MathChallenge extends K9Activity {

    public final String[] MATH_SIGNS = {"+", "-"};

    private String sign = "+";
    private int leftNumber;
    private int rightNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_challenge);

        //Get number pickers
        NumberPicker signInput = (NumberPicker) findViewById(R.id.sign);
        NumberPicker leftNumberInput = (NumberPicker) findViewById(R.id.left_number);
        NumberPicker rightNumberInput = (NumberPicker) findViewById(R.id.right_number);

        // set the number pickers up
        setupMathInput(leftNumberInput, 0, 9);
        setupMathInput(rightNumberInput, 0, 9);
        setupMathInput(signInput, 0, MATH_SIGNS.length - 1);

        // makes the input use array as display instead
        signInput.setDisplayedValues(MATH_SIGNS);

    }

    private void setupMathInput(NumberPicker picker, int minVal, int maxVal) {
        // sets min number or array start position
        picker.setMinValue(minVal);

        // sets max number or array end position
        picker.setMaxValue(maxVal);

        // makes the scroll wheel input wrap if min/max val reached
        picker.setWrapSelectorWheel(true);
    }

}
