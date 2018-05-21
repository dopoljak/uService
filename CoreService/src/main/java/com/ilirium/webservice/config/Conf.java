package com.ilirium.webservice.config;

import com.typesafe.config.*;
import org.slf4j.*;

import javax.inject.*;
import java.util.*;

public abstract class Conf {

    @Inject
    private Logger LOGGER;

    protected Config config;

    public Conf() {
        this.config = ConfigFactory.load();
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    private void validate() {
        LOGGER.info("Validating configuration: {}", config.origin());
        config.checkValid(config);
        for (Map.Entry configEntry : config.entrySet()) {
            LOGGER.info("{} : {}", configEntry.getKey(), configEntry.getValue());
        }
        LOGGER.info("Configuration validated.");
    }

}
