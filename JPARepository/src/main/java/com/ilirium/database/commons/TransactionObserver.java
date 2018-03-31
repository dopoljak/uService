package com.ilirium.database.commons;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

/**
 * @author dpoljak
 */
@ApplicationScoped
public class TransactionObserver {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TransactionObserver.class);

    void processTxSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) AbstractEntity entity) {
        LOGGER.info("After success = TransactionPhase.AFTER_SUCCESS");
    }

    void processTxFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) AbstractEntity emp) {
        LOGGER.info("After failure = TransactionPhase.AFTER_FAILURE");
    }

}
