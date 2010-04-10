package com.dominikdorn.interceptors;

import org.junit.Ignore;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
@Ignore
public class TestClassImpl implements TestClass {

    @Override
    public String someMethod(String msg)
    {
        System.out.println("message is " + msg);
        return "foo";
    }
}
