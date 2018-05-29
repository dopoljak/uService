package com.ilirium.webservice.commons;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * POM Version reader
 *
 * @author dpoljak
 */
public class VersionUtils {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(VersionUtils.class);
    public static final String META_INF_MANIFEST_LOCATION = "/META-INF/MANIFEST.MF";

    public static Map<String, String> readWarManifest(ServletContext context) {
        if(context == null) {
            LOGGER.error("ServletContext is null!");
            return Collections.EMPTY_MAP;
        }

        try {
            try (InputStream is = context.getResourceAsStream(META_INF_MANIFEST_LOCATION)) {
                return readManifest(is);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return Collections.EMPTY_MAP;
    }

    public static Map<String, String> readManifest(InputStream inputStream) {
        Map<String, String> map = new HashMap<>();
        if(inputStream == null) {
            LOGGER.error("InputStream is null!");
            return map;
        }
        try {
            Manifest manifest = new Manifest(inputStream);
            Attributes attributes = manifest.getMainAttributes();
            for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
                map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        } catch (IOException ex) {
            LOGGER.error("", ex);
        }
        return map;
    }

    /**
     * Get artifact version id from 'pom.properties' file
     *
     * @param groupId:    group of artefacte
     * @param artifactId: artefact id
     * @return
     */
    public static String getVersion(String groupId, String artifactId) {
        String pom_path = String.format("/META-INF/maven/%s/%s/pom.properties", groupId, artifactId);
        Properties p = new Properties();
        InputStream is = null;

        try {
            is = VersionUtils.class.getResourceAsStream(pom_path);
            p.load(is);
            return p.getProperty("version", "");
        } catch (Exception e) {
            LOGGER.warn("POMUtils: Can't load version from artifactId : " + artifactId);
        } finally {
            closeQuietly(is);
        }

        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(pom_path);
            p.load(is);
            return p.getProperty("version", "");
        } catch (Exception e) {
            LOGGER.warn("ClassLoader: Can't load version from artifactId : " + artifactId);
        } finally {
            closeQuietly(is);
        }

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pom_path);
            p.load(is);
            return p.getProperty("version", "");
        } catch (Exception e) {
            LOGGER.warn("CurrentThread: Can't load version from artifactId : " + artifactId);
        } finally {
            closeQuietly(is);
        }

        return null;
    }

    private static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
    }
}
