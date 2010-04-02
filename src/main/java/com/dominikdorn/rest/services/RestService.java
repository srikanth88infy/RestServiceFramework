package com.dominikdorn.rest.services;

import com.dominikdorn.rest.dao.AbstractJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RestService {

    Map<Class, AbstractJpaDao> daoMap = new HashMap<Class, AbstractJpaDao>();

    private EntityManagerFactory emf;

    public RestService(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public AbstractJpaDao getDao(Class clazz)
    {
        AbstractJpaDao dao;
        if(!daoMap.containsKey(clazz))
        {
            dao = new AbstractJpaDao(clazz);
            daoMap.put(clazz, dao);
        }
        dao =  daoMap.get(clazz);
        EntityManager em = emf.createEntityManager();

        dao.setEm(em);

        return dao;
    }



    public Object handleGetById(Class clazz, Object id, String mediaType)
    {
        return getDao(clazz).getById((Long) id);
//        return "handleGetById(Class clazz, Object id, String mediaType)";
    }

    public List handleGetAll(Class clazz, String mediaType)
    {
        return getDao(clazz).getAll();
//        return "    public String handleGetAll(Class clazz, String mediaType)";
    }

    public String handleSearch(Class clazz, Map<String,String> attributes, String mediaType)
    {
        return "    public String handleSearch(Class clazz, Map<String,String> attributes, String mediaType)";
    }

    // create with id
    public String handlePostOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)
    {
        return "    public String handlePostOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)";
    }

    // create without id
    public String handlePostOnResource(Class clazz, String data, String mediaType)
    {
        return "    public String handlePostOnResource(Class clazz, String data, String mediaType)";
    }

    // update with id
    public String handlePutOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)
    {
        return "    public String handlePutOnObject(Class clazz, Object id, Map<String,String> attributes, String mediaType)";
    }

    // replace collection
    public String handlePutOnResource(Class clazz, String data, String mediaType)
    {
        return "    public String handlePutOnResource(Class clazz, String data, String mediaType)";
    }




    public String handleDeleteOnObject(Class clazz, Object id, String mediaType)
    {
        return "";
    }

    public String handleDeleteOnResource(Class clazz, String mediaType)
    {
        return "";
    }

}
