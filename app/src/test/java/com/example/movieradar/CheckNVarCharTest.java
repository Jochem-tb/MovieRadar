package com.example.movieradar;

import org.junit.Test;
import static org.junit.Assert.*;

public class CheckNVarCharTest {

    @Test //Valide Test
    public void testCheckNVarCharWithValidInput() {
        assertTrue(Datatest.checkNVarChar("abc", 5));
        assertTrue(Datatest.checkNVarChar("123", 3));
        assertTrue(Datatest.checkNVarChar("Hello", 10));
    }

    @Test //Lege String test
    public void testCheckNVarCharWithEmptyString() {
        assertFalse(Datatest.checkNVarChar("", 5));
    }

    @Test //NULL value test
    public void testCheckNVarCharWithNullInput() {
        assertFalse(Datatest.checkNVarChar(null, 5));
    }

    @Test //String is te lang
    public void testCheckNVarCharWithTooLongString() {
        assertFalse(Datatest.checkNVarChar("ThisIsTooLong", 5));
    }

    @Test //Test met Unicode
    public void testCheckNVarCharWithUnicodeCharacter() {
        assertTrue(Datatest.checkNVarChar("Hello😊", 10)); // Assuming it's valid for this method
    }

}
