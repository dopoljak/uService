package com.ilirium.basic.config;

import com.typesafe.config.*;
import org.slf4j.*;

import java.util.*;

public abstract class Conf {

    private Logger LOGGER = LoggerFactory.getLogger(Conf.class);

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
