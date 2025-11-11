package com.myorganisation.gcafe.exception;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException() {
        super("OTP verification failed");
    }

    public InvalidOtpException(String m) {
        super(m);
    }
}
