package ar.edu.unlam.smartshop.servicios;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConection {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Fallo la inicializacion de SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
