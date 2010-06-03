package com.dominikdorn.tuwien.evs.rest.services;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class EncodingNegotiator {

    public OutputType detect(String contentType)
    {
        if(contentType == null)
            return OutputType.UNSUPPORTED;

        if(contentType.indexOf("application/xml") > -1)
            return OutputType.XML;

        if(contentType.indexOf("application/json") > -1)
            return OutputType.JSON;

        if(contentType.indexOf("application/x-www-form-urlencoded") > -1)
            return OutputType.XHTML;

        return OutputType.UNSUPPORTED;
    }
}
