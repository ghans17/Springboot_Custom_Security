package com.argusoft.authmodule.MenuManagement.exceptions;


public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}