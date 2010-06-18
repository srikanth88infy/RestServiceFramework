package com.dominikdorn.rest.gateway.utils;

import java.util.ArrayList;
import java.util.List;

public class JSONGatewayResult {

    private String time;
    
    private String host;
    
    private String port;
    
    private String error;
    
    private List<JSONStorageResult> results = new ArrayList<JSONStorageResult>();

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<JSONStorageResult> getResults() {
        return results;
    }

    public void setResults(List<JSONStorageResult> results) {
        this.results = results;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
    
    
}
