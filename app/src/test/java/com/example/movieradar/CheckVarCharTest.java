package com.example.movieradar;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CheckVarCharTest {

    @Test //Valide tests
    public void testCheckVarCharWithValidInput() {
        assertTrue(Datatest.checkVarChar("abc", 5));
        assertTrue(Datatest.checkVarChar("123", 3));
        assertTrue(Datatest.checkVarChar("Hello", 10));
    }

    @Test //NULL test
    public void testCheckVarCharWithNullInput() {
        assertFalse(Datatest.checkVarChar(null, 5));
    }

    @Test //Lege String
    public void testCheckVarCharWithEmptyString() {
        assertTrue(Datatest.checkVarChar("", 0)); // Assuming 0 is always a valid length for an empty string
        assertFalse(Datatest.checkVarChar("", 5));
    }

    @Test //String te lang!
    public void testCheckVarCharWithTooLongString() {
        assertFalse(Datatest.checkVarChar("ThisIsTooLong", 5));
    }

    @Test //String met UniCode
    public void testCheckVarCharWithUnicodeCharacter() {
        assertFalse(Datatest.checkVarChar("Hello😊", 10));
    }

}
