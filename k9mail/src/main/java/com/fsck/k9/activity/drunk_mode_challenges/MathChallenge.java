package com.fsck.k9.activity.drunk_mode_challenges;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fsck.k9.activity.K9Activity;
import com.fsck.k9.R;

public class MathChallenge extends K9Activity {

    public final String[] MATH_SIGNS = {"+", "-"};
    public final String[] MATH_OPERATION_SYMBOLS = {"+", "−", "×"};

    private String sign = "+";
    private int leftNumber;
    private int rightNumber;

    private String equation;
    private int solution;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_challenge);

        // Set up the math equation
        // Get view
        TextView equationView = (TextView) findViewById(R.id.math_challenge_equation);

        // Generate equation and set text
        equation = generateEquation();
        equationView.setText(equation);


        // Set up the Number pickers
        // Get number pickers
        NumberPicker signInput = (NumberPicker) findViewById(R.id.sign);
        NumberPicker leftNumberInput = (NumberPicker) findViewById(R.id.left_number);
        NumberPicker rightNumberInput = (NumberPicker) findViewById(R.id.right_number);

        // set the number pickers up
        setupMathInput(leftNumberInput, 0, 9);
        setupMathInput(rightNumberInput, 0, 9);
        setupMathInput(signInput, 0, MATH_SIGNS.length - 1);

        // makes the input use array as display instead
        signInput.setDisplayedValues(MATH_SIGNS);


        // Setup submit button TODO actually submit
        Button submitAnswerButton = (Button) findViewById(R.id.submit_math_answer_button);
        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.err.println("45454 solution" + solution);
            }
        });


    }

    private String generateEquation() {
        //get random numbers from -9 to +9
        int firstNumber = (int)(Math.random() * 19) - 9;
        int secondNumber = (int)(Math.random() * 19) - 9;

        //get random operation
        int operation = (int)(Math.random() * MATH_OPERATION_SYMBOLS.length - 1);

        switch (operation) {
            case 0: solution = firstNumber + secondNumber;
            break;
            case 1: solution = firstNumber - secondNumber;
            break;
            case 2: solution = firstNumber * secondNumber;
        }

        return firstNumber + " " + MATH_OPERATION_SYMBOLS[operation] + " " + secondNumber;
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
