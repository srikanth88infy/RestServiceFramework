package com.dominikdorn.interceptors;

import com.dominikdorn.rest.interceptors.LoggingInterceptor;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class InterceptorTest {


    public InterceptorTest() {
    }

    @Test
    public void interceptorTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        TestClass bar = new TestClassImpl();

//        InvocationHandler handler = new LoggingInterceptor();

//        Class proxyClass = Proxy.getProxyClass(TestClassImpl.class.getClassLoader(), new Class[]{ TestClass.class } );
        Class proxyClass = Proxy.getProxyClass(bar.getClass().getClassLoader(), new Class[]{ TestClass.class } );

//        TestClass foo = (TestClass) proxyClass.getConstructor(new Class[]{InvocationHandler.class}).newInstance(new Object[] { handler } );
//        TestClass foo = (TestClass) Proxy.newProxyInstance(TestClass.class.getClassLoader(), new Class[]{TestClass.class}, handler);

        TestClass foo2 = (TestClass) Proxy.newProxyInstance(TestClassImpl.class.getClassLoader(), TestClassImpl.class.getInterfaces(), new LoggingInterceptor(bar) );

//        foo.someMethod("blabla");

        foo2.someMethod("blabla");

//        System.out.println("foo.someMethod(\"someMSG\") = " + foo.someMethod("someMSG"));

//        System.out.println("foo = " + foo);

    }
}
