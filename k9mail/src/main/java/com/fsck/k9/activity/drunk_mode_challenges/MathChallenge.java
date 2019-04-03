package com.fsck.k9.activity.drunk_mode_challenges;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fsck.k9.activity.K9Activity;
import com.fsck.k9.R;

public class MathChallenge extends K9Activity {

    enum Operation {
        ADD("+"), SUBTRACT("−"), MULTIPLY("x");

        private String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return this.symbol;
        }
    }

    public final String[] MATH_SIGNS = {"+", "-"};
    public final int SECONDS_TO_COMPLETE_CHALLENGE = 15;

    private ProgressBar countdownView;
    private TextView equationView;
    private TextView descriptionView;

    private NumberPicker signInput;
    private NumberPicker leftNumberInput;
    private NumberPicker rightNumberInput;
    private Button submitAnswerButton;

    private String sign = "+";
    private int leftNumber;
    private int rightNumber;

    private String equation;
    private int solution;

    private CountDownTimer countdown;
    private int currentProgress = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_challenge);

        descriptionView = (TextView) findViewById(R.id.math_challenge_description);

        // Set up the math equation
        // Get view
        equationView = (TextView) findViewById(R.id.math_challenge_equation);

        // Generate equation and set text
        equation = generateEquation();
        equationView.setText(equation);


        // Set up the Number pickers
        // Get number pickers
        signInput = (NumberPicker) findViewById(R.id.sign);
        leftNumberInput = (NumberPicker) findViewById(R.id.left_number);
        rightNumberInput = (NumberPicker) findViewById(R.id.right_number);

        // set the number pickers up
        setupMathInput(signInput, 0, MATH_SIGNS.length - 1);
        setupMathInput(leftNumberInput, 0, 9);
        setupMathInput(rightNumberInput, 0, 9);

        // makes the input use array as display instead
        signInput.setDisplayedValues(MATH_SIGNS);

        // setup change listeners
        setupInputChangeListeners();


        // Setup submit button
        submitAnswerButton = (Button) findViewById(R.id.submit_math_answer_button);
        submitAnswerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkSolution();
            }
        });


        // Setup progress bar and countdown
        setupAndStartCountdown();
    }

    private void checkSolution() {
        int answer = Integer.parseInt(sign + leftNumber + rightNumber);
        if (answer != solution) {
            System.err.println("454545: oof");
        } else {
            System.err.println("454545: OMG, right");
        }
    }

    private String generateEquation() {
        //get random numbers from -9 to +9
        int firstNumber = (int)(Math.random() * 19) - 9;
        int secondNumber = (int)(Math.random() * 19) - 9;

        //get random operation
        int randOperationChoice = (int)(Math.random() * Operation.values().length);
        Operation operation = Operation.values()[randOperationChoice];

        switch (operation) {
            case ADD:
                solution = firstNumber + secondNumber;
                break;
            case SUBTRACT:
                solution = firstNumber - secondNumber;
                break;
            case MULTIPLY:
                solution = firstNumber * secondNumber;
        }

        return firstNumber + " " + operation + " " + secondNumber;
    }

    private void setupMathInput(NumberPicker picker, int minVal, int maxVal) {
        // sets min number or array start position
        picker.setMinValue(minVal);

        // sets max number or array end position
        picker.setMaxValue(maxVal);

        // makes the scroll wheel input wrap if min/max val reached
        picker.setWrapSelectorWheel(true);
    }

    private void setupInputChangeListeners() {
        signInput.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                sign = MATH_SIGNS[newVal];
            }
        });

        leftNumberInput.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                leftNumber = newVal;
            }
        });

        rightNumberInput.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                rightNumber = newVal;
            }
        });
    }

    private void setupAndStartCountdown() {
        countdownView = (ProgressBar)findViewById(R.id.countdown);

        long timeToCompleteMillis = SECONDS_TO_COMPLETE_CHALLENGE * 1000;
        long checkTime = timeToCompleteMillis / 100;
        countdown = new CountDownTimer(timeToCompleteMillis, checkTime) {
            @Override
            public void onTick(long l) {
                ++currentProgress;
                countdownView.setProgress(currentProgress);
            }

            @Override
            public void onFinish() {
                currentProgress = 100;
                countdownView.setProgress(currentProgress);
            }
        };

        countdown.start();
    }

}
