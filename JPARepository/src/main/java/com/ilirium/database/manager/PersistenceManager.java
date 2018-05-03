package com.ilirium.database.manager;

import org.slf4j.*;

import javax.annotation.*;
import javax.enterprise.context.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import javax.persistence.*;

@ApplicationScoped
public class PersistenceManager {

    public static final String PERSISTANCE_NAME = "AppPersistenceUnit";

    @Inject
    private Logger LOGGER;

    @Inject
    private EntityManagerFactory emf;

    @PreDestroy
    public void destroy() {
        LOGGER.debug("Closing entity manager factory.");
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Produces
    @RequestScoped
    @Default
    public EntityManager createEntityManager() {
        LOGGER.debug("Creating entity manager");
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        LOGGER.debug("Closing entity manager: open = {}, active = {}", em.isOpen(), em.getTransaction() != null ? em.getTransaction().isActive() : null);

        if (em.isOpen()) {
            try {
                if (em.getTransaction().isActive()) {
                    LOGGER.debug("Rolling back active transaction due to dispose of entity manager.");
                    em.getTransaction().rollback();
                }
            } catch (Exception e) {
                LOGGER.error("Rollback transaction failed: ", e);
            }

            try {
                em.clear();
                em.close();
                LOGGER.debug("Entity manager closed.");
            } catch (Exception e) {
                LOGGER.debug("Error closing entity manager: ", e);
            }
        }
    }

}
