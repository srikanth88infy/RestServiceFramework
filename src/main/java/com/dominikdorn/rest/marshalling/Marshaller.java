package com.dominikdorn.rest.marshalling;

import com.dominikdorn.rest.services.OutputType;
import com.dominikdorn.rest.services.RemotingError;

import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class Marshaller {

    private Map<OutputType, MarshallingStrategy> strategies;
    private OutputType defaultOutputType;

    public Marshaller(Map<OutputType, MarshallingStrategy> strategies, OutputType defaultOutputType) {
        this.strategies = strategies;
        this.defaultOutputType = defaultOutputType;
    }

    public String serialize(Object obj, OutputType type) throws RemotingError {
        if(strategies.containsKey(type))
        {
            return strategies.get(type).serialize(obj, null);
        }
        throw new RemotingError(1,"No MarshallingStrategy for requested OutputType found"); // TODO marshall this with default output type
    }

    public Object deSerialize(String data, OutputType type) throws RemotingError
    {
        if(strategies.containsKey(type))
        {
            return strategies.get(type).deSerialize(data, null);
        }
        throw new RemotingError(1,"No MarshallingStrategy for requested OutputType found"); // TODO marshall this with default output type
    }

}
