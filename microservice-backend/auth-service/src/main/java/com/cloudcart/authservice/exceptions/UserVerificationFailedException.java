package com.cloudcart.authservice.exceptions;

public class UserVerificationFailedException extends Exception{

    public UserVerificationFailedException(String message) {
        super(message);
    }
}
