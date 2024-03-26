package com.example.movieradar;

import org.junit.Test;
import static org.junit.Assert.*;

public class CheckCijferTest {

    @Test //Valide test
    public void testCheckCijferWithValidCijfer() {
        short validCijfer = 5;
        assertTrue(Datatest.checkCijfer(validCijfer));
    }

    @Test //Invalide test
    public void testCheckCijferWithInvalidCijfer() {
        short invalidCijfer = 0; // Less than the minimum allowed value
        assertFalse(Datatest.checkCijfer(invalidCijfer));

        invalidCijfer = 11; // Greater than the maximum allowed value
        assertFalse(Datatest.checkCijfer(invalidCijfer));
    }

    @Test //Valide Boudaries
    public void testCheckCijferWithBoundaryValues() {
        short minValue = 1;
        short maxValue = 10;

        assertTrue(Datatest.checkCijfer(minValue));
        assertTrue(Datatest.checkCijfer(maxValue));
    }

}
