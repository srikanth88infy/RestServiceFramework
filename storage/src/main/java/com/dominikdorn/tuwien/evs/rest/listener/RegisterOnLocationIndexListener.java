package com.dominikdorn.tuwien.evs.rest.listener;

import com.dominikdorn.rest.registration.RegisteringException;
import com.dominikdorn.rest.registration.RegistrationService;
import com.dominikdorn.rest.registration.RegistrationServiceImpl;
import com.dominikdorn.rest.registration.RegistrationThread;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegisterOnLocationIndexListener implements ServletContextListener{
    String ownAddress;
    String ownPort;
    String locationIndexAddress;
    String locationIndexPort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ownAddress = (String) System.getProperties().get("storage.host");
        ownPort = (String) System.getProperties().get("storage.port");
        locationIndexAddress = (String) System.getProperties().get("locationIndex.host");
        locationIndexPort = (String) System.getProperties().get("locationIndex.port");

        if(ownAddress == null)
            throw new RuntimeException("required System-Property storage.host not defined");

        if(ownPort == null)
            throw new RuntimeException("required System-Property storage.port not defined");

        if(locationIndexAddress == null)
            throw new RuntimeException("required System-Property locationIndex.host not defined");

        if(locationIndexPort == null)
            throw new RuntimeException("required System-Property locationIndex.port not defined");

        System.out.println("ownAddress = " + ownAddress);
        System.out.println("ownPort = " + ownAddress);

        System.out.println("locationIndexAddress = " + locationIndexAddress);
        System.out.println("locationIndexPort = " + locationIndexPort);

        RegistrationThread thread = new RegistrationThread(2000, -1, locationIndexAddress, locationIndexPort, ownAddress, ownPort);
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RegistrationService service = new RegistrationServiceImpl();
        service.unRegisterOnParent(locationIndexAddress, locationIndexPort, ownAddress, ownPort);
    }
}
