package com.ilirium.basic.config;

import org.slf4j.*;

import java.io.*;
import java.util.*;

public class ConfigUtils {

    private Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

    public Properties loadFromFile(String location) {
        LOGGER.info("Loading properties from file: {}", location);
        Properties p = new Properties();
        try (InputStream fis = new FileInputStream(location)) {
            p.load(fis);
        } catch (Exception e) {
            LOGGER.error("Loading configuration from file : ", e);
        }
        return p;
    }

    public Properties loadFromJar(String location) {
        LOGGER.info("Loading properties from JAR: {}", location);
        Properties p = new Properties();
        try (InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(location)) {
            p.load(resourceStream);
        } catch (Exception e) {
            LOGGER.error("Loading configuration from JAR file : ", e);
        }
        return p;
    }

}
