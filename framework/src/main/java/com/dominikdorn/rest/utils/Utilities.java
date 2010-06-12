package com.dominikdorn.rest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.registration.RegisteringException;
import com.dominikdorn.rest.services.OutputType;

public class Utilities {

    public static boolean ping(final String addr) {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet get = new HttpGet("http://" + addr + "/ping");

        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                return true;
            }
        } catch (final IOException e) {

        }

        return false;

    }

    public static List<String> getClients(final String addr, final Marshaller marshaller) {

        final HttpClient client = new DefaultHttpClient();
        final HttpGet get = new HttpGet("http://" + addr + "/clients");
        System.out.println(get.getURI().toString());
        final List<String> result = new ArrayList<String>();

        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                final StringBuffer resp = new StringBuffer();
                final InputStream in = entity.getContent();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    resp.append(line);
                }
                reader.close();

                System.out.println(resp.toString());
                System.out.println(Arrays.deepToString(response.getAllHeaders()));

                result.addAll((List<String>) marshaller.deSerialize(resp.toString(), List.class, OutputType.JSON));

            }
        } catch (IOException e) {
            // what to do here?
            // e.printStackTrace();
        }

        return result;
    }

    public static HttpResponse search(final String addr, final String criteria) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", criteria));
        params.add(new BasicNameValuePair("description", criteria));


       
        
        final HttpClient client = new DefaultHttpClient();
        final HttpPost post = new HttpPost("http://" + addr + "/search");
        post.getParams().setParameter("name", criteria);
        post.setHeader("Accept:", "application/json");
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            post.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = client.execute(post);
            
            return response;
            
        } catch (IOException e) {
            // what to do here?
            // e.printStackTrace();
            return null;
        }
    }

}
