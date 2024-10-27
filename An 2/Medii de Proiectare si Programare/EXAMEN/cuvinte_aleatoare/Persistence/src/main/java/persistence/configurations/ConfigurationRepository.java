package persistence.configurations;
import model.Configuration;
import model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class ConfigurationRepository implements IConfigurationRepository {
    private static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    public ConfigurationRepository() {
        logger.traceEntry("Initializing Configuration Repository");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        try {
            sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            logger.error(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

        logger.traceExit();
    }

    @Override
    public Configuration getRandomConfiguration() {
        Random random = new Random();
        logger.traceEntry("Getting random configuration");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Configuration> configuration = session.createQuery("from Configuration", Configuration.class).list();
                transaction.commit();
                logger.traceExit("Successfully got random configuration");
                return configuration.get(random.nextInt(configuration.size()));
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                logger.error("Error while getting random configuration: " + ex);
            }
        }
        logger.traceExit("Error");
        return null;
    }

    @Override
    public int add(Configuration configuration) {
        logger.traceEntry("Adding configuration: " + configuration.toString());

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                int id = (int) session.save(configuration);
                transaction.commit();
                logger.traceExit("Successfully added configuration");
                return id;
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                logger.error("Error while adding configuration: " + ex);
            }
        }
        logger.traceExit("Error");
        return -1;
    }

    @Override
    public Configuration getLastConfiguration() {
        logger.traceEntry("Getting the last configuration with the largest ID");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Configuration configuration = session.createQuery("from Configuration order by id desc", Configuration.class)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                logger.traceExit("Successfully got the last configuration with the largest ID");
                return configuration;
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                logger.error("Error while getting the last configuration with the largest ID: " + ex);
            }
        }

        logger.traceExit("Error");
        return null;
    }

    @Override
    public void updateConfiguration(Configuration configuration) {
        logger.traceEntry("Updating conf");
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.update(configuration);
                transaction.commit();
                logger.traceExit("Successfully updated conf");
                return;
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                logger.error("Error while updating conf: " + ex);
            }
        }
        logger.traceExit("Error");
    }

    @Override
    public Configuration findById(Integer id) {
        logger.traceEntry("find an game by id");
        try(Session session=sessionFactory.openSession()){
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                Configuration configuration =session.createQuery("from Configuration where id=:id", Configuration.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                transaction.commit();
                logger.traceExit(configuration);
                return configuration;
            }
            catch (RuntimeException ex){
                if(transaction!=null)
                    transaction.rollback();
                logger.error("ERROR");
            }

        }
        return null;
    }

}