package com.omkar.getitdone.util

object InputValidator {
    fun isInputValid(input: String?): Boolean {
        return !input?.trim().isNullOrEmpty() && input!!.length > 1
    }
}