package com.dominikdorn.interceptors;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class TestClassImpl implements TestClass {

    @Override
    public String someMethod(String msg)
    {
        System.out.println("message is " + msg);
        return "foo";
    }
}
