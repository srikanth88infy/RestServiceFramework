package com.dominikdorn.rest.services;

import com.dominikdorn.tuwien.evs.rest.annotations.Restful;
import org.scannotation.AnnotationDB;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class AnnotationScanner {

    public Map<String, Class> getRegisteredObjectMap(URL classpath)
    {
        AnnotationDB db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        db.setScanFieldAnnotations(true);
        db.setScanParameterAnnotations(false);
        db.setScanMethodAnnotations(false);

        db.addIgnoredPackages(
                 "com.dominikdorn.tuwien.evs.rest.dao"
                ,"com.dominikdorn.tuwien.evs.rest.services"
                ,"com.dominikdorn.tuwien.evs.rest.annotations"
                ,"com.dominikdorn.tuwien.evs.rest.listeners"
                ,"com.dominikdorn.tuwien.evs.rest.requestHandling"
        );
        try {
            db.scanArchives(new URL[]{classpath});
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        Set<String> restfulClasses = db.getAnnotationIndex().get(Restful.class.getName());

        Map<String, Class> result = new HashMap<String, Class>();



        System.out.println("found theses classes");
        if(restfulClasses != null)
        {
            for(String s : restfulClasses)
            {
                try {
                    System.out.println("s = " + s);
                    result.put(s, Class.forName(s));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }

        }
        else
        {
            System.out.println("found no classes");
        }

        return result;
    }



}
