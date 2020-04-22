package util;

import model.Car;
import model.DailyReport;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


public class DBHelper {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration(Properties properties) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Car.class);
        configuration.addAnnotatedClass(DailyReport.class);
        configuration.setProperties(properties);
        return configuration;
    }


    private static Properties getProperties() {
        Properties properties = new Properties();

        ClassLoader classLoader = DBHelper.class.getClassLoader();
        File configFile = new File(Objects.requireNonNull(classLoader.getResource("db.properties")).getFile());

        try (InputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error to access db.properties");
        }
        return properties;
    }


    private static SessionFactory createSessionFactory() {
        Configuration configuration = getConfiguration(getProperties());
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
