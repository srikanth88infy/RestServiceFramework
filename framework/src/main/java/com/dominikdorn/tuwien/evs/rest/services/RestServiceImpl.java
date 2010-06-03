package com.dominikdorn.tuwien.evs.rest.services;

import com.dominikdorn.tuwien.evs.rest.dao.AbstractJpaDao;
import com.dominikdorn.tuwien.evs.rest.dao.GenericDao;
import com.dominikdorn.tuwien.evs.rest.dao.GenericJpaDao;
import com.dominikdorn.tuwien.evs.rest.dao.TransactionalJpaDao;
import com.dominikdorn.tuwien.evs.rest.interceptors.LoggingInterceptor;

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
public class RestServiceImpl implements RestService {

    Map<Class, GenericDao> daoMap = new HashMap<Class, GenericDao>();

    private EntityManagerFactory emf;

    public RestServiceImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public GenericDao getDao(Class clazz)
    {
        GenericDao dao;

        GenericJpaDao realDao;
        EntityManager em = emf.createEntityManager();

        if(!daoMap.containsKey(clazz))
        {
            realDao= (GenericJpaDao) LoggingInterceptor.getProxy(
                    new AbstractJpaDao(clazz)
            );          
            daoMap.put(clazz, realDao);
        }
        dao =  daoMap.get(clazz);

        return new TransactionalJpaDao(em, (GenericJpaDao) dao);

//        return dao;
    }



    @Override
    public Object handleGetById(Class clazz, Object id) throws RemotingError
    {
        Object result = getDao(clazz).getById((Long) id);
        if(result == null)
            throw new NotFoundRemotingError("Object with Id "+ id + " of Type " + clazz.getSimpleName() + " not found");
        return result;
    }

    @Override
    public List handleGetAll(Class clazz) throws RemotingError
    {
        return getDao(clazz).getAll();
    }

    @Override
    public List handleSearch(Class clazz, Map<String, String> attributes) throws RemotingError
    {
        return getDao(clazz).findByAttributes(attributes);
    }

    // create with id
    @Override
    public Object handlePostOnObject(Class clazz, Object id, Object o)  throws RemotingError
    {

        GenericDao dao = getDao(clazz);
        Object o2 = dao.getById((Long) id);

        if(o2 != null)
        {
            throw new AlreadyExistsRemotingError();
        }

        o = dao.persist(o);

        
        return o;
    }

    // create without id
    @Override
    public Object handlePostOnResource(Class clazz, Object o) throws RemotingError
    {
        // create a object and persist it.

        GenericDao dao = getDao(clazz);
        dao.persist(o);

        return o;
    }

    // update with id
    @Override
    public Object handlePutOnObject(Class clazz, Object id, Object o) throws RemotingError
    {
        GenericDao dao = getDao(clazz);

        Object o2 = dao.getById((Long) id);
        if(o2 != null)
        {
            dao.update(o);
        }
        else
        {
            dao.persist(o);
        }

        return o;
    }

    // replace collection
    @Override
    public String handlePutOnResource(Class clazz, String data) throws RemotingError
    {
        throw new UnsupportedOperationRemotingError();
    }




    @Override
    public void handleDeleteOnObject(Class clazz, Object id) throws RemotingError
    {
        GenericDao dao = getDao(clazz);
        Object o = dao.getById((Long) id);
        if(o == null)
            throw new NotFoundRemotingError("Object with Id " + id + " not found ");

        dao.delete(o);

    }

    @Override
    public int handleDeleteOnResource(Class clazz) throws RemotingError
    {
        GenericDao dao = getDao(clazz);
        List allObjects = dao.getAll();
        int num = 0;
        for(Object o : allObjects)
        {
            dao.delete(o);
            num++;
        }
        return num;
    }

}
