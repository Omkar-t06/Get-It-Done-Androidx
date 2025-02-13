package com.omkar.getitdone

import com.omkar.getitdone.util.InputValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidationTest {

    @Test
    fun inputValidator_returnsFalseWhenEmpty() {
        val result = InputValidator.isInputValid("")
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenNull() {
        val result = InputValidator.isInputValid(null)
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyWhiteSpace() {
        val result = InputValidator.isInputValid("   ")
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsFalseWhenOnlyOneNoneWhiteSpaceCharacter() {
        val result = InputValidator.isInputValid("1")
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenMoreThanOneNonWhiteSpaceCharacter() {
        val result = InputValidator.isInputValid("more than one character")
        assertTrue(result)
    }

    @Test
    fun inputValidator_returnsTrueWhenTwoNonWhiteSpaceCharacter() {
        val result = InputValidator.isInputValid("ab")
        assertTrue(result)
    }

}