package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.activity.drunk_mode_challenges.MathChallenge;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class MathChallengeTest {

    private MathChallenge mathChallengeActivity;

    @Before
    public void setUp() throws Exception {
        mathChallengeActivity = new MathChallenge();
    }

    @Test
    public void testGenerateEquation() {

        // check addition case
        String addResult = mathChallengeActivity.generateEquation(MathChallenge.Operation.ADD, 4, 5);
        assertEquals("4 + 5", addResult);
        assertEquals(9, mathChallengeActivity.getSolution());

        // check subtraction case
        String subtractResult = mathChallengeActivity.generateEquation(MathChallenge.Operation.SUBTRACT, 4, 5);
        assertEquals("4 − 5", subtractResult);
        assertEquals(-1, mathChallengeActivity.getSolution());

        // check multiply case
        String multiplyResult = mathChallengeActivity.generateEquation(MathChallenge.Operation.MULTIPLY, 4, 5);
        assertEquals("4 x 5", multiplyResult);
        assertEquals(20, mathChallengeActivity.getSolution());
    }

    @Test
    public void generateEquation_DisplaysNegativeNumbers() {
        String result = mathChallengeActivity.generateEquation(MathChallenge.Operation.MULTIPLY, -2, 6);
        assertEquals("-2 x 6", result);
        assertEquals(-12, mathChallengeActivity.getSolution());
    }

    @Test
    public void generateEquation_DoesNotDisplayZeroWithNegative() {
        String result = mathChallengeActivity.generateEquation(MathChallenge.Operation.MULTIPLY, -0, 0);
        assertEquals("0 x 0", result);
        assertEquals(0, mathChallengeActivity.getSolution());
    }

    @Test
    public void testPercentCompleted() {
        // none completed
        assertEquals(0, mathChallengeActivity.percentCompleted(100, 100));

        // all completed
        assertEquals(100, mathChallengeActivity.percentCompleted(100, 0));

        //random case
        assertEquals(30, mathChallengeActivity.percentCompleted(100, 70));

        //different total
        assertEquals(50, mathChallengeActivity.percentCompleted(30, 15));
    }

    @Test
    public void testAnswerIsCorrect() {
        mathChallengeActivity.generateEquation(MathChallenge.Operation.MULTIPLY, 4, -6);
        assertEquals(-24, mathChallengeActivity.getSolution());

        // check wrong answer
        mathChallengeActivity.setInputValues("+", 1, 2);
        assertFalse(mathChallengeActivity.answerIsCorrect());

        // check opposite sign answer is still wrong
        mathChallengeActivity.setInputValues("+", 2, 4);
        assertFalse(mathChallengeActivity.answerIsCorrect());

        // check right answer is correct
        mathChallengeActivity.setInputValues("-", 2, 4);
        assertTrue(mathChallengeActivity.answerIsCorrect());

        // check single digit solution case
        mathChallengeActivity.generateEquation(MathChallenge.Operation.ADD, 2, 2);
        assertEquals(4, mathChallengeActivity.getSolution());
        mathChallengeActivity.setInputValues("+", 0, 4);
        assertTrue(mathChallengeActivity.answerIsCorrect());
    }

    @Test
    public void answerIsCorrect_signDoesNotMatter_whenAnswerIsZero() {
        mathChallengeActivity.generateEquation(MathChallenge.Operation.ADD, 0, 0);
        assertEquals(0, mathChallengeActivity.getSolution());

        // sign shouldn't effect answer of zero
        mathChallengeActivity.setInputValues("+", 0, 0);
        assertTrue(mathChallengeActivity.answerIsCorrect());

        mathChallengeActivity.setInputValues("-", 0, 0);
        assertTrue(mathChallengeActivity.answerIsCorrect());
    }
}
