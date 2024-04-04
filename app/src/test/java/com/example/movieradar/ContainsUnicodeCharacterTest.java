package com.example.movieradar;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContainsUnicodeCharacterTest {

    @Test //String MET unicode test
    public void testContainsUnicodeCharacterWithUnicodeCharacter() {
        assertTrue(Datatest.containsUnicodeCharacter("Hello😊"));
    }

    @Test //String zonder Unicode test
    public void testContainsUnicodeCharacterWithoutUnicodeCharacter() {
        assertFalse(Datatest.containsUnicodeCharacter("Hello"));
    }

    @Test //String met NULL
    public void testContainsUnicodeCharacterWithNullInput() {
        assertFalse(Datatest.containsUnicodeCharacter(null));
    }

    @Test //Een lege String test
    public void testContainsUnicodeCharacterWithEmptyString() {
        assertFalse(Datatest.containsUnicodeCharacter(""));
    }

}