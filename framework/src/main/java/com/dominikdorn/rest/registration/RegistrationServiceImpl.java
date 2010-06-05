package com.dominikdorn.rest.registration;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public boolean registerOnParent(String parentHost, String parentPort, String myHost, String myPort) throws RegisteringException {

        HttpPost post = new HttpPost("http://" + parentHost + ":" + parentPort + "/register");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("host", myHost));
        params.add(new BasicNameValuePair("port", myPort));


        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            throw new RegisteringException(e);
        }

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            throw new RegisteringException(e);
        }

        switch(response.getStatusLine().getStatusCode())
        {
            case 200:
            {
                //ok
                try {
                    String res = EntityUtils.toString(response.getEntity(), "UTF-8");
                    if(res.contains("OK"))
                        return true;
                } catch (IOException e) {
                    throw new RegisteringException("Could not parse response from server", e);
                }
            }
            break;

            default:
                throw new RegisteringException("Unrecognized response from parent while registering");
        }

        return false;
    }

    @Override
    public void unRegisterOnParent(String parentHost, String parentPort, String myHost, String myPort) {
        HttpPost post = new HttpPost("http://" + parentHost + ":" + parentPort + "/unRegister");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("host", myHost));
        params.add(new BasicNameValuePair("port", myPort));


        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpClient client = new DefaultHttpClient();
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
