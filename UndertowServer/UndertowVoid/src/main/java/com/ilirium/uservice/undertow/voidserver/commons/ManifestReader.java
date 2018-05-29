package com.ilirium.uservice.undertow.voidserver.commons;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ManifestReader {

    public static String buildDate() {
        try {
            Manifest mf = new Manifest();
            mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
            Attributes atts = mf.getMainAttributes();
            return atts.getValue("Build-Date");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
