package com.ansbeno.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Provides a factory for Hibernate sessions. This class is based on the
 * singleton design pattern. The instance of the class is created only once and
 * is used throughout the application.
 * <p>
 * The static initializer is used to create the instance of the class. This
 * approach is used to ensure that the instance is created only once and is
 * thread-safe.
 * </p>
 */
public class HibernateUtil {

      /**
       * The instance of the class. This variable is volatile because it is
       * accessed from multiple threads.
       */
      private static volatile SessionFactory sessionFactory;

      /**
       * Private constructor to prevent instantiation from outside the class.
       */
      private HibernateUtil() {
      }

      /**
       * Static initializer to create the instance of the class. This method is
       * called only once when the class is first loaded into memory.
       */
      static {
            try {
                  /**
                   * Create the session factory. The configuration is loaded from
                   * the hibernate.cfg.xml file.
                   */
                  sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception ex) {
                  /**
                   * Throw an exception if the session factory cannot be created.
                   */
                  throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
            }
      }

      /**
       * Returns the instance of the class.
       *
       * @return the session factory
       */
      public static SessionFactory getSessionFactory() {
            return sessionFactory;
      }

      /**
       * Closes the session factory. This method should be called when the
       * application is shut down.
       */
      public static void shutdown() {
            if (sessionFactory != null) {
                  /**
                   * Close the session factory. This will release all the
                   * resources held by the session factory.
                   */
                  sessionFactory.close();
            }
      }
}
