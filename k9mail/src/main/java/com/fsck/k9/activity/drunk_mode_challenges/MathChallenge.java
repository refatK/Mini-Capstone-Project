package com.fsck.k9.activity.drunkmode;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.fsck.k9.activity.K9Activity;
import com.fsck.k9.R;


public class MathChallenge extends K9Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_challenge);

        //Get number pickers
        NumberPicker leftNumber = (NumberPicker) findViewById(R.id.left_number);
        NumberPicker rightNumber = (NumberPicker) findViewById(R.id.right_number);

        //Set the minimum value of NumberPicker
        leftNumber.setMinValue(0);
        rightNumber.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        leftNumber.setMaxValue(9);
        rightNumber.setMaxValue(9);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        leftNumber.setWrapSelectorWheel(true);
        rightNumber.setWrapSelectorWheel(true);
    }

}
