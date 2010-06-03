package com.dominikdorn.tuwien.evs.rest.interceptors;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class LoggingInterceptor implements InvocationHandler {


    public static Object getProxy(Object o) {
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), new LoggingInterceptor(o));
    }

    Object realObject;

    public LoggingInterceptor(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        try {
            Date before, after;

            System.out.println("===============================");
            System.out.println(realObject.getClass().getCanonicalName() + "#" + method.getName() + "(" + ((objects == null) ? "null" : objects.toString()) + ")" + ": entered ");
            before = new Date();
            Object result = method.invoke(realObject, objects);
            after = new Date();
            long executionTime = after.getTime() - before.getTime();
            System.out.println(realObject.getClass().getCanonicalName() + "#" + method.getName() + "(" + ((objects == null) ? "null" : objects.toString()) + ")" + ": returned (" + executionTime + " ms)");
            System.out.println("===============================");
            return result;
        }
        catch (IllegalArgumentException iae)
        {
            throw iae.getCause();
        }
        catch (InvocationTargetException ite)
        {
            throw ite.getTargetException();
        }
        catch(RuntimeException re)
        {
            throw re;
        }
        catch (Exception e)
        {
            throw e;
        }
        catch (Throwable t) {
            throw t;
        }
    }
}
