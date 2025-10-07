package com.myorganisation.gcafe.exception;

public class ChefNotFoundException extends RuntimeException {
    public ChefNotFoundException() {
        super("Chef doesn't exist");
    }

    public ChefNotFoundException(String m) {
        super(m);
    }
}
