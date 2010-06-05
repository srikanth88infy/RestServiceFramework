package com.dominikdorn.rest.registration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegisteringException extends Exception {
    public RegisteringException(Exception e) {
        super(e);
    }

    public RegisteringException(String s) {
        super(s);
    }

    public RegisteringException(String s, Exception e) {
        super(s,e);
    }
}
