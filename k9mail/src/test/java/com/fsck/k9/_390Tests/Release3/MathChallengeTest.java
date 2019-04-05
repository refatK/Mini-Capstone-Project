package com.fsck.k9._390Tests.Release3;


import com.fsck.k9.activity.drunk_mode_challenges.MathChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathChallengeTest {

    private MathChallenge mathChallengeActivity;

    @Before
    public void setUp() throws Exception {
        mathChallengeActivity = new MathChallenge();
    }

    @Test
    public void generatingEquation_WorksCorrectly() {

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
}
