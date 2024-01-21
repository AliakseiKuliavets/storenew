package com.aliakseikul.storenew.exeption.checkMethods;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CheckTest {

    // ----------Test methods valueNullOrEmptyTest() -----1--------------------------------
    @Test
    void valueNullOrEmptyPositiveTest() {
        assertFalse(Check.valueNullOrEmpty("adad"));
    }

    @Test
    void valueNullOrEmptyValueIsEmptyTest() {
        assertTrue(Check.valueNullOrEmpty(""));
    }

    @Test
    void valueNullOrEmptyValueIsNullTest() {
        assertTrue(Check.valueNullOrEmpty(null));
    }

    // ----------Test methods checkIdLength() -------------------------------------

    @Test
    void checkIdLengthIsValidTest() {
        assertFalse(Check.checkIdLength("c2bfcb8e-af96-43c8-88e2-56f3a78e2436"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "12345678765432",
            "1213",
            "c2bfcb8e-af96-43c8-88e2-56f3a78e24362342"
    })
    void checkIdLengthIsInvalidTest(String value) {
        assertTrue(Check.checkIdLength(value));
    }

    @Test
    void checkIdLengthIsEmptyTest() {
        assertTrue(Check.checkIdLength(""));
    }

    @Test
    void checkIdLengthIsNullTest() {
        assertTrue(Check.checkIdLength(null));
    }

    // ----------Test methods checkNumber() -------------------------------------

    @Test
    void checkNumberIsValidTest() {
        assertFalse(Check.checkNumber("1234"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "123a",
            "-1231",
            "+48",
            "!12"
    })
    void checkNumberIsInvalidTest(String value) {
        assertTrue(Check.checkNumber(value));
    }
}