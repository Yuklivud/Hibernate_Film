package org.film.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration()
                    .configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
