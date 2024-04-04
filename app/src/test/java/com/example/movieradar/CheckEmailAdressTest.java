package com.example.movieradar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CheckEmailAdressTest {

    @Test //Valide Test
    public void testCheckEmailAddressWithValidEmail() {
        assertTrue(Datatest.checkEmailAddress("test@example.com"));
        assertTrue(Datatest.checkEmailAddress("user123@gmail.com"));
    }

    @Test //Invalide Tests
    public void testCheckEmailAddressWithInvalidEmail() {
        assertFalse(Datatest.checkEmailAddress("invalid.email@")); // Missing top-level domain
        assertFalse(Datatest.checkEmailAddress("user@.com")); // Missing domain
        assertFalse(Datatest.checkEmailAddress("user@domain.")); // Missing top-level domain
        assertFalse(Datatest.checkEmailAddress("user@domain@.com")); // Multiple @ symbols
        assertFalse(Datatest.checkEmailAddress("user@domain..com")); // Double dot in domain
        assertFalse(Datatest.checkEmailAddress("user@domain@com")); // Multiple @ symbols without a dot
        assertFalse(Datatest.checkEmailAddress("user@123")); // Numeric domain
        assertFalse(Datatest.checkEmailAddress("user@domain..com")); // Double dot in domain
        assertFalse(Datatest.checkEmailAddress("user@domain@.com")); // Multiple @ symbols
        assertFalse(Datatest.checkEmailAddress("user@.domain.com")); // Dot after @
        assertFalse(Datatest.checkEmailAddress(null)); // Null input
        assertFalse(Datatest.checkEmailAddress("")); // Empty string
    }
}
