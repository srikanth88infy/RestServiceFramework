package com.dominikdorn.rest.gateway.utils;

public class JSONStorageResult {

    private String time;
    
    private String host;
    
    private String port;
    
    private String result;

    private String error;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setError(String error) {
        this.error = error;
        
    }
    
    public String getError() {
        return this.error;
    }
}
