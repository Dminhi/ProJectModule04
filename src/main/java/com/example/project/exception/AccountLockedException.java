package com.example.project.exception;

public class AccountLockedException extends Exception {
    public AccountLockedException(String message) {
        super(message);
    }
}
