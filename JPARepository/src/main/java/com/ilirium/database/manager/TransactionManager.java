package com.ilirium.database.manager;

import org.slf4j.*;

import javax.inject.*;
import javax.persistence.*;

public class TransactionManager {

    @Inject
    private Logger LOGGER;

    @Inject
    private PersistenceManager persistenceManager;

    private EntityManager entityManager;

    public TransactionManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void begin() {
        begin(entityManager);
    }

    public void begin(EntityManager em) {
        LOGGER.debug("Starting transaction.");
        em.getTransaction().begin();
    }

    private void commit(EntityManager em) {
        LOGGER.debug("Commiting transaction.");
        try {
            if (em != null && em.isOpen()) {
                EntityTransaction et = em.getTransaction();
                if (et != null && et.isActive()) {
                    et.commit();
                    LOGGER.debug("Transaction commited.");
                }

            }
        } catch (Exception e) {
            LOGGER.error("Transaction commit failed", e);
            throw e;
        }
    }

    public void commitFinish(EntityManager em) {
        LOGGER.debug("Commiting transaction and finishing.");
        try {
            commit(em);
        } finally {
            persistenceManager.close(em);
        }
    }

    public void commitSilently(EntityManager em) {
        try {
            commitFinish(em);
        } catch (Exception e) {
            LOGGER.warn("Silencing commit exception.");
        }
    }

    private void rollback(EntityManager em) {
        LOGGER.debug("Rolling back transaction.");
        try {
            if (em != null && em.isOpen()) {
                EntityTransaction et = em.getTransaction();
                if (et != null && et.isActive()) {
                    et.rollback();
                    LOGGER.debug("Transaction rolled back.");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Transaction rollback failed", e);
            throw e;
        }
    }

    public void rollbackFinish(EntityManager em) {
        LOGGER.debug("Rolling back transaction.");
        try {
            rollback(em);
        } finally {
            persistenceManager.close(em);
        }
    }

    public void rollbackSilently(EntityManager em) {
        try {
            rollback(em);
        } catch (Exception e) {
            LOGGER.warn("Silencing rollback exception.");
        }
    }

    public void commitWithRollbackRecover(EntityManager em) {
        try {
            commit(em);
        } catch (Exception e) {
            rollback(em);
            throw e;
        }
    }

    public void commitWithRollbackRecoverSilently(EntityManager em) {
        try {
            commitWithRollbackRecover(em);
        } catch (Exception e) {
            LOGGER.warn("Silencing commit exception");
        }
    }

    /**
     * Commit transaction and continue managing entities (leave EntityManager open) -->
     * entitymanager-per-request pattern.
     * https://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/transactions.html#transactions-basics-uow
     */
    public void commitContinue(EntityManager em) {
        LOGGER.debug("Commit and continue transaction.");
        try {
            commit(em);
            begin(em);
        } catch (Exception e) {
            LOGGER.error("Commit and continue failed: ", e);
            throw e;
        }
    }

    /**
     * Rollback transaction and continue managing entities (leave EntityManager open) -->
     * entitymanager-per-request pattern.
     * https://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/transactions.html#transactions-basics-uow
     */
    public void rollbackContinue(EntityManager em) {
        LOGGER.debug("Rollback and continue transaction.");
        try {
            rollback(em);
            begin(em);
        } catch (Exception e) {
            LOGGER.error("Commit and continue failed: ", e);
            throw e;
        }
    }

}
