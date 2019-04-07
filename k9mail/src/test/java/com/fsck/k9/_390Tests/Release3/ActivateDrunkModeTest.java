package com.fsck.k9._390Tests.Release3;

import com.fsck.k9.service.ActivateDrunkMode;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ActivateDrunkModeTest {

    private ActivateDrunkMode activateDrunkModeTest;

    @Before
    public void setUp() throws Exception {
        activateDrunkModeTest = new ActivateDrunkMode();
    }

    @Test
    public void testIsItGoTime() {
        // check wrong bool
        activateDrunkModeTest.setStartTime(1100);
        activateDrunkModeTest.setEndTime(1380);
        activateDrunkModeTest.setCurrentTime(800);
        assertFalse(activateDrunkModeTest.isItGoTime());

        // check wrong bool for 'day overflow' case
        activateDrunkModeTest.setStartTime(1320);
        activateDrunkModeTest.setEndTime(480);
        activateDrunkModeTest.setCurrentTime(1020);
        assertFalse(activateDrunkModeTest.isItGoTime());

        // check right bool is correct
        activateDrunkModeTest.setStartTime(1080);
        activateDrunkModeTest.setEndTime(1350);
        activateDrunkModeTest.setCurrentTime(1200);
        assertTrue(activateDrunkModeTest.isItGoTime());

        // check right bool for 'day overflow' case, before midnight
        activateDrunkModeTest.setStartTime(1290);
        activateDrunkModeTest.setEndTime(360);
        activateDrunkModeTest.setCurrentTime(1400);
        assertTrue(activateDrunkModeTest.isItGoTime());

        // check right bool for 'day overflow' case, after midnight
        activateDrunkModeTest.setStartTime(1290);
        activateDrunkModeTest.setEndTime(360);
        activateDrunkModeTest.setCurrentTime(100);
        assertTrue(activateDrunkModeTest.isItGoTime());
    }

    @Test
    public void testCorrectActivation() {

    }

}
