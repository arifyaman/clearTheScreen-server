package com.xlipstudio.cleanthescreen.server.hibernate;

import com.xlipstudio.cleanthescreen.server.conf.ServerConfigurations;
import com.xlipstudio.cleanthescreen.server.hibernate.model.GameConf;
import com.xlipstudio.cleanthescreen.server.hibernate.model.GameMatch;
import com.xlipstudio.cleanthescreen.server.hibernate.model.Player;
import com.xlipstudio.cleanthescreen.server.hibernate.model.User;
import com.xlipstudio.cleanthescreen.server.hibernate.model.sub.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
import java.util.logging.Level;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static HibernateUtil instance = new HibernateUtil();

    public static HibernateUtil getInstance() {
        return instance;
    }

    public Session session;


    public static void init() {
        instance = new HibernateUtil();
    }

    public HibernateUtil() {

        try {
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

            Configuration configuration = new Configuration();
            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, ServerConfigurations.getIntance().hibarnate.driverClass);
            settings.put(Environment.URL, ServerConfigurations.getIntance().hibarnate.url);
            settings.put(Environment.USER, ServerConfigurations.getIntance().hibarnate.username);
            settings.put(Environment.PASS, ServerConfigurations.getIntance().hibarnate.password);
            settings.put(Environment.SHOW_SQL, ServerConfigurations.getIntance().hibarnate.showSql);
            settings.put(Environment.AUTO_CLOSE_SESSION, ServerConfigurations.getIntance().hibarnate.showSql);
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, ServerConfigurations.getIntance().hibarnate.sessionContextClass);
            settings.put(Environment.HBM2DDL_AUTO, ServerConfigurations.getIntance().hibarnate.ddlAuto);

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(GameConf.class);
            configuration.addAnnotatedClass(GameMatch.class);
            configuration.addAnnotatedClass(Player.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            this.session = sessionFactory.openSession();
            ServerConfigurations.getIntance().gameConf = getModel(1, GameConf.class);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    public User saveOrUpdateUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the user object
            if (user.getId() == 0) {
                user.setId((Long) session.save(user));
            } else {
                session.saveOrUpdate(user);
            }

            // commit transaction
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public <T extends BaseEntity> T saveOrUpdate(T object, Class<T> type) {
        Transaction transaction = session.beginTransaction();

        if(object.getId() == 0) {
            object.setId((Long) session.save(object));
        }
        session.saveOrUpdate(object);
        transaction.commit();
        return object;
    }

    public <T> T getModel(long id, Class<T> tClass) {

        return session.get(tClass, id);

    }

    public User searchByUserKey(String userKey) {
        Query query = session.createQuery("from User where userKey=:userKey");
        query.setParameter("userKey", userKey);
        User user = (User) query.uniqueResult();
        return user;
    }

    public Player searchPlayerByUser(User user) {
        Query query = session.createQuery("from Player where user=:user");
        query.setParameter("user", user);
        Player player = (Player) query.uniqueResult();
        return player;
    }


}
