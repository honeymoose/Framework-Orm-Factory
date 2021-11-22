package com.ossez.framework.common.dao;


import com.ossez.framework.common.DataObject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Factor for DAO
 *
 * @author YuCheng Hu
 */
public class Factory {
    private static Logger logger = LoggerFactory.getLogger(Factory.class);

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    private static int indexBatchSize = 100;

    // blocks the commit function from being called - this is useful for unit testing.
    private static boolean noCommit = false;

    // executes a rollback instead of a commit when commit is called - this is useful for unit testing.
    private static boolean autoRollback = false;

    // prevents the connection from being closed. This is useful for unit testing.
    private static boolean noClose = false;

    /**
     * Get Hibernate connection Session
     *
     * @return
     */
    public static Session getSession() {
        return Factory.getFactory().getCurrentSession();
    }

    /**
     * Get session connection Get
     *
     * @return
     */
    public static SessionFactory getFactory() {
        if (Factory.sessionFactory == null)
            Factory.sessionFactory = Factory.initSession();

        return Factory.sessionFactory;
    }

    /**
     * Get SessionFactory
     *
     * @return
     */
    public static SessionFactory initSession() {
        Configuration configuration = new Configuration();
        configuration.configure();

        serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory;
    }

    /**
     * Begin DataBase Transaction
     */
    public static void beginTransaction() {

        if (!(Factory.getSession().getTransaction() != null && Factory.getSession().getTransaction().isActive())) {
            Factory.getSession().getTransaction().begin();
        } else {

        }
    }

    public static void commitTransaction() {
        if (isAutoRollback()) {
            Factory.rollbackTransaction();
            return;
        }
        if (Factory.getSession().getTransaction() != null && Factory.getSession().getTransaction().isActive()) {
            if (!noCommit)
                Factory.getSession().getTransaction().commit();
        }
    }

    public static void rollbackTransaction() {
        if (Factory.getSession().getTransaction() != null && Factory.getSession().getTransaction().isActive())
            Factory.getSession().getTransaction().rollback();
    }

    /**
     * Gets an object of type T from Hibernate.
     *
     * @param <T>
     * @param classEntity
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> classEntity, int id) {
        Object object = Factory.getSession().get(classEntity, id);

        if (object == null)
            return null;

        return (T) object;
    }

    /**
     * Saves the specified object to the database.
     *
     * @param object
     */
    public static void save(DataObject object) {
        if (object == null)
            throw new NullPointerException("Object supplied is null");

        if (object.getId() > 0)
            Factory.getSession().saveOrUpdate(object);
        else
            Factory.getSession().save(object);
    }

    /**
     * Deletes the specified object.
     *
     * @param object
     */
    public static void delete(DataObject object) {
        Factory.getSession().delete(object);
    }

    /**
     * Creates an hibernate criteria object which is used to query against objects.
     *
     * @param classArg
     * @return
     */
    public static <T> Criteria createCriteria(Class<T> classArg) {
        return Factory.getSession().createCriteria(classArg);
    }

    /**
     * Creates an hibernate criteria object with the specified alias.
     *
     * @param classArg
     * @param alias
     * @param <T>
     * @return
     */
    public static <T> Criteria createCriteria(Class<T> classArg, String alias) {
        return Factory.getSession().createCriteria(classArg, alias);
    }

    /**
     * Serializes an object into a byte array.
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream serializer = new ObjectOutputStream(stream);
            serializer.writeObject(object);
            serializer.close();
        } catch (IOException ex) {
            Factory.handleException(ex);
        }

        byte[] result = stream.toByteArray();
        stream.close();
        return result;
    }

    private static void handleException(IOException ex) {
        // TODO Auto-generated method stub

    }

    /**
     * Merges the specified instance.
     *
     * @param obj
     */
    public static void merge(DataObject obj) {
        Factory.getSession().merge(obj);
    }

    /**
     * Refreshes the specified instance.
     *
     * @param obj
     */
    public static void refresh(DataObject obj) {
        Factory.getSession().refresh(obj);
    }

    /**
     * Saves or updates the object.
     *
     * @param obj
     */
    public static void saveOrUpdate(DataObject obj) {
        Factory.getSession().saveOrUpdate(obj);
    }

    /**
     * Evicts the specified object from the session.
     *
     * @param obj
     */
    public static void evict(DataObject obj) {
        Factory.getSession().evict(obj);
    }


    /**
     * Creates a hibernate query.
     *
     * @param query
     * @return
     */
    public static Query createQuery(String query) {
        return Factory.getSession().createQuery(query);
    }

    public static FullTextSession getFullTextSession() {
        return Search.getFullTextSession(Factory.getSession());
    }

    /**
     * Closes the session factory. Should only be called when an application is closing.
     */
    public static void close() {
        if (!isNoClose())
            Factory.getFactory().close();
    }


    /**
     * @param noCommit the noCommit to set
     */
    public static void setNoCommit(boolean noCommit) {
        Factory.noCommit = noCommit;
    }

    /**
     * @return the noCommit
     */
    public static boolean isNoCommit() {
        return noCommit;
    }

    /**
     * @param autoRollback the autoRollback to set
     */
    public static void setAutoRollback(boolean autoRollback) {
        Factory.autoRollback = autoRollback;
    }

    /**
     * @return the autoRollback
     */
    public static boolean isAutoRollback() {
        return autoRollback;
    }

    /**
     * @param noClose the noClose to set
     */
    public static void setNoClose(boolean noClose) {
        Factory.noClose = noClose;
    }

    /**
     * @return the noClose
     */
    public static boolean isNoClose() {
        return noClose;
    }
}
