package com.example.movieradar;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheckValidNumberTest {

    @Test //Valide test
    public void testCheckValidNumberWithValidInput() {
        assertTrue(Datatest.checkValidNumber("5", 10));
        assertTrue(Datatest.checkValidNumber("10", 10));
    }

    @Test //Invalid Test
    public void testCheckValidNumberWithInvalidInput() {
        assertFalse(Datatest.checkValidNumber("0", 10)); // Zero is not allowed
        assertFalse(Datatest.checkValidNumber("-5", 10)); // Negative number is not allowed
        assertFalse(Datatest.checkValidNumber("abc", 10)); // Non-numeric input
        assertFalse(Datatest.checkValidNumber(null, 10)); // Null input
        assertFalse(Datatest.checkValidNumber("", 10)); // Empty string
        assertFalse(Datatest.checkValidNumber("11", 10)); // Exceeds the limit
    }

}
