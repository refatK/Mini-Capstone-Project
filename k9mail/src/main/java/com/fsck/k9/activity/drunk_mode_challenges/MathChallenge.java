package com.fsck.k9.activity.drunk_mode_challenges;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fsck.k9.R;

public class MathChallenge extends DrunkModeChallengeActivity {

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
    public final int SECONDS_TO_COMPLETE_CHALLENGE = 20;
    public final int MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE = 1500;
    public final int COMPLETED_PROGRESS = 100;

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
    private boolean timeIsUp = false;

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
        setupMathInput(signInput, MATH_SIGNS.length - 1);
        setupMathInput(leftNumberInput, 9);
        setupMathInput(rightNumberInput, 9);

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

    private void checkSolution() {
        int answer = Integer.parseInt(sign + leftNumber + rightNumber);
        if (answer == solution) {
            winChallenge();
        } else {
            loseChallenge();
        }
    }

    @Override
    protected void loseChallenge() {
        complete = true;

        // if time ran out, progress bar would be complete
        if (timeIsUp) {
            String timeOutMessage = getString(R.string.drunk_mode_challenge_timeout, SECONDS_TO_COMPLETE_CHALLENGE);
            changeViewOnComplete(Color.YELLOW, Color.BLACK, timeOutMessage);
        } else {
            changeViewOnComplete(Color.RED, Color.WHITE, getString(R.string.drunk_mode_challenge_failed));
        }

        loseSound.start();
        loseWithDelay(MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE);
    }

    @Override
    protected void winChallenge() {
        complete = true;
        changeViewOnComplete(Color.GREEN, Color.BLACK, getString(R.string.drunk_mode_challenge_success));
        winSound.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE);
    }

    private void timeout() {
        timeoutSound.start();
        loseChallenge();
    }

    private void setupMathInput(NumberPicker picker, int maxVal) {
        // sets min number or array start position
        picker.setMinValue(0);

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

        final long timeToCompleteMillis = SECONDS_TO_COMPLETE_CHALLENGE * 1000;
        long checkTime = timeToCompleteMillis / 100;
        countdown = new CountDownTimer(timeToCompleteMillis, checkTime) {
            @Override
            public void onTick(long millisLeft) {
                if (complete || !active) {
                    cancel();
                }

                long millisCompleted = timeToCompleteMillis - millisLeft;
                int percentComplete = (int) Math.round(((double) millisCompleted / timeToCompleteMillis) * 100);

                countdownView.setProgress(percentComplete);
            }

            @Override
            public void onFinish() {
                timeIsUp = true;
                countdownView.setProgress(COMPLETED_PROGRESS);
                timeout();
            }
        };

        countdown.start();
    }

    private void changeViewOnComplete(int primaryColor, int secondaryColor, String message) {
        descriptionView.setBackgroundColor(primaryColor);
        descriptionView.setTextColor(secondaryColor);
        descriptionView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        descriptionView.setText(message);

        countdownView.setProgressTintList(ColorStateList.valueOf(primaryColor));

        equationView.setTextColor(primaryColor);

        submitAnswerButton.setBackgroundColor(primaryColor);
        submitAnswerButton.setTextColor(secondaryColor);
    }

}
