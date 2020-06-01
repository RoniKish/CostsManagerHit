package com.costsmanagerhit.model;

import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represent the class which implements the IUserDAO interface, adda/validates users to/from DB
 */
public class UserDAOHibernate implements IUserDAO{

    private static IUserDAO instance;
    private final SessionFactory factory;

    private UserDAOHibernate() {
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    /**
     * @return the instance of this object, if no instance exists, create a new one.
     */
    public static IUserDAO getInstance() {
        if(instance == null){
            instance= new UserDAOHibernate();
        }
        return instance;
    }

    /**
     *
     * @param userName user name to validate
     * @param password password to validate
     * @return the user object if the validation succeeded, otherwise - null
     */
    @Override
    public User validateUserAndPassword(String userName,String password) {
        Session session = null;
        User u = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            //Checks if the user exists or details are valid
            Query query=session.createQuery("FROM User U WHERE U.username = :username and U.password= :password")
                    .setString("username",userName)
                    .setString("password",password);
            List<?> users = query.list();
            if(users.size() == 0)//The user does not exists or wrong password - return null
                throw new UserDAOException("Username '" + userName +"' is not valid or wrong password");
            //The user exists - return him
            u = (User)users.get(0);
        }
        catch (HibernateException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive()) tx.rollback();
        }
        catch (UserDAOException e){
            e.printStackTrace();
        }
        finally
        {
            if(session!=null) session.close();
        }
        return u;
    }

    /**
     *
     * @param userName the user name to add
     * @param password the password to add
     * @return user object if the registration succeeded, otherwise - null
     * @throws UserDAOException in case of taken username
     */
    @Override
    public User addUser(String userName,String password) throws UserDAOException {
        Session session = null;
        User user = null;
        try {
            session = factory.openSession();
            session.beginTransaction();

            if(!userNameExists(userName))
                throw new UserDAOException("An attempt to register userName: '" + userName +"' was done, user already exists.");

            User userTemp = new User(userName, password);
            session.save(userTemp);
            session.getTransaction().commit();
            Query query = session.createQuery("from User U where U.username= :username")
                    .setString("username",userName);
            List<?> users = query.list();
            Iterator<?> i = users.iterator();
            user = (User)users.get(0);
        }
        catch (HibernateException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            if(session!=null)
                session.close();
        }
        return user;
    }

    /**
     *
     * @param userName user name to validate
     * @return false if the user exists, otherwise - true
     */
    @Override
    public boolean userNameExists(String userName) {
        Session session = null;
        try
        {
            session = factory.openSession();
            session.beginTransaction();

            Query query = session.createQuery("FROM User U WHERE U.username = :username")
                    .setString("username",userName);
            List<?> users = query.list();
            if(users.size() != 0)
                return false;
        }
        catch (HibernateException e)
        {
            Transaction tx = Objects.requireNonNull(session).getTransaction();
            if (tx.isActive())
                tx.rollback();
        }
        finally
        {
            if(session!=null) session.close();
        }
        return true;
    }
}
