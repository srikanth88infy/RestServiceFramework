package com.dominikdorn.rest.services;

import com.dominikdorn.tuwien.evs.rest.annotations.Restful;
import com.dominikdorn.tuwien.evs.rest.annotations.Searchable;
import org.scannotation.AnnotationDB;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
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
            throw new RuntimeException("failed to scan through the classpath. Is your harddrive ok?", e);
        }


        Set<String> restfulClasses = db.getAnnotationIndex().get(Restful.class.getName());

        Map<String, Class> result = new HashMap<String, Class>();

        if(restfulClasses != null)
        {
            for(String s : restfulClasses)
            {
                try {
                    Class clazz = Class.forName(s);
                    Restful r = (Restful) clazz.getAnnotation(Restful.class);
                    result.put(  r.value(),clazz  );
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Couldn't load a class we previously found. This should never happen",
                            e);
                }
            }
        }

        return result;
    }


    public Set<String> getSearchableFieldsForClass(Class clazz){
        Set<String> result = new HashSet<String>();

        for(Field f : clazz.getDeclaredFields())
        {
            Searchable sa = f.getAnnotation(Searchable.class);
            if(sa != null)
            {
                result.add(f.getName());
            }
        }
        return result;
    }

}
