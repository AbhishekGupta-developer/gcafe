package com.myorganisation.gcafe.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException() {
        super("Dish doesn't exist");
    }

    public DishNotFoundException(String m) {
        super(m);
    }
}
