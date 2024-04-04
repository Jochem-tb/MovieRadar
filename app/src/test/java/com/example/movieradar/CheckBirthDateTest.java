package com.example.movieradar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.LocalDate;

public class CheckBirthDateTest {

    @Test //Valide test
    public void testCheckBirthDateWithValidDate() {
        LocalDate validDate = LocalDate.now().minusYears(Datatest.getMinage() + 1);
        assertTrue(Datatest.checkBirthDate(validDate));
    }

    @Test //Invalide Date
    public void testCheckBirthDateWithInvalidDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        assertFalse(Datatest.checkBirthDate(futureDate)); // Date in the future

        LocalDate underageDate = LocalDate.now().minusYears(Datatest.getMinage() - 1);
        assertFalse(Datatest.checkBirthDate(underageDate)); // Underage date
    }

}


