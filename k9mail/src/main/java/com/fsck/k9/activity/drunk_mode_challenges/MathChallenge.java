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

import org.jetbrains.annotations.TestOnly;

/**
 * Drunk mode challenge where the user must solve a random math equation in a given time frame
 * to win
 */
public class MathChallenge extends DrunkModeChallengeActivity {

    public enum Operation {
        ADD("+"), SUBTRACT("−"), MULTIPLY("x");

        cause build fail

        private String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return this.symbol;
        }
    }

    public enum Sign {
        POSITIVE("+"), NEGATIVE("-");

        private String symbol;

        Sign(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return this.symbol;
        }

        public static String[] toStringArray() {
            return new String[]{POSITIVE.toString(), NEGATIVE.toString()};
        }
    }

    public static final int SECONDS_TO_COMPLETE_CHALLENGE = 20;
    public static final int MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE = 1500;
    public static final int COMPLETED_PROGRESS_VALUE = 100;

    private ProgressBar countdownView;
    private TextView equationView;
    private TextView descriptionView;

    private NumberPicker signInput;
    private NumberPicker leftNumberInput;
    private NumberPicker rightNumberInput;
    private Button submitAnswerButton;

    private Sign sign = Sign.POSITIVE;
    private int leftNumber;
    private int rightNumber;

    private int solution;
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
        String equation = generateRandomEquation();
        equationView.setText(equation);


        // Set up the Number pickers
        // Get number pickers
        signInput = (NumberPicker) findViewById(R.id.sign);
        leftNumberInput = (NumberPicker) findViewById(R.id.left_number);
        rightNumberInput = (NumberPicker) findViewById(R.id.right_number);

        // set the number pickers up
        setupMathInput(signInput, Sign.values().length - 1);
        setupMathInput(leftNumberInput, 9);
        setupMathInput(rightNumberInput, 9);

        // makes the input use array as display instead
        signInput.setDisplayedValues(Sign.toStringArray());


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

    /**
     * Creates a random equation to solve
     */
    private String generateRandomEquation() {
        //get random numbers from -9 to +9
        int firstNumber = generateRandomSingleDigitNumber();
        int secondNumber = generateRandomSingleDigitNumber();

        //get random operation
        int randOperationChoice = (int) (Math.random() * Operation.values().length);
        Operation operation = Operation.values()[randOperationChoice];

        return generateEquation(operation, firstNumber, secondNumber);
    }

    /**
     * Generate a simple equation
     *
     * @param operation    the math operation
     * @param firstNumber  number left of operator
     * @param secondNumber number right of operator
     * @return the equation in string form for view
     */
    public String generateEquation(Operation operation, int firstNumber, int secondNumber) {
        // get solution which depends on the operation
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

        // return equation in string form for view
        return firstNumber + " " + operation + " " + secondNumber;
    }

    /**
     * Generate an integer in range [-9, 9]
     *
     * @return the generated number
     */
    private int generateRandomSingleDigitNumber() {
        int amountOfNumbers = 19;
        int offset = -9;

        return (int) (Math.random() * amountOfNumbers) + offset;
    }

    /**
     * Handles what happens depending on if users answer was correct or not
     */
    private void checkSolution() {
        setAnswerValuesFromInput();

        if (answerIsCorrect()) {
            winSound.start();
            winChallenge();
        } else {
            loseSound.start();
            loseChallenge();
        }
    }

    /**
     * Instead of using listeners, set value when needed
     */
    public void setAnswerValuesFromInput() {
        sign = Sign.values()[signInput.getValue()];
        leftNumber = leftNumberInput.getValue();
        rightNumber = rightNumberInput.getValue();
    }

    /**
     * Compares users answer to actual equation solution
     */
    public boolean answerIsCorrect() {
        int answer = Integer.parseInt(sign.toString() + leftNumber + rightNumber);
        return answer == solution;
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

        loseWithDelay(MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE);
    }

    @Override
    protected void winChallenge() {
        complete = true;

        changeViewOnComplete(Color.GREEN, Color.BLACK, getString(R.string.drunk_mode_challenge_success));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, MILLIS_DELAY_WHEN_CHALLENGE_COMPLETE);
    }

    /**
     * What happens when the time to complete the challenge has been reached
     */
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

        // initially set value to start position
        picker.setValue(0);
    }

    private void setupAndStartCountdown() {
        countdownView = (ProgressBar) findViewById(R.id.countdown);

        final long timeToCompleteMillis = SECONDS_TO_COMPLETE_CHALLENGE * 1000;
        long checkTime = timeToCompleteMillis / 100;
        CountDownTimer countdown = new CountDownTimer(timeToCompleteMillis, checkTime) {
            @Override
            public void onTick(long millisLeft) {
                if (complete || !active) {
                    cancel();
                }

                countdownView.setProgress(percentCompleted(timeToCompleteMillis, millisLeft));
            }

            @Override
            public void onFinish() {
                timeIsUp = true;
                countdownView.setProgress(COMPLETED_PROGRESS_VALUE);
                timeout();
            }
        };

        countdown.start();
    }

    /**
     * Calculates percentage of completion in range [0, 100]
     *
     * @param total     the number to reach to be considered complete
     * @param remaining the amount left to complete
     */
    public int percentCompleted(long total, long remaining) {
        long completed = total - remaining;
        return (int) Math.round(((double) completed / total) * 100);
    }

    /**
     * Change UI visuals to denote something happened
     *
     * @param primaryColor   color that stands out, want user to notice
     * @param secondaryColor color for text on top of primaryColor
     * @param message        message to use in the description area of the view
     */
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

    public int getSolution() {
        return solution;
    }

    /**
     * Since inputs are only for user through ui, this method allows setting inputs programmatically
     * for testing
     */
    @TestOnly
    public void setInputValues(Sign sign, int leftNumber, int rightNumber) {
        this.sign = sign;
        this.leftNumber = leftNumber;
        this.rightNumber = rightNumber;
    }

}
