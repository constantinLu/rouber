package com.orange.rouber.util;


import javax.validation.ValidationException;

public class Validator {

    public static <T> T checkNotNull(T obj) {
        if (obj == null)
            throw new ValidationException("Value {} cannot be null");
        return obj;
    }

}
