package com.ilirium.uservice.undertow.voidserver.commons;

import org.pmw.tinylog.Level;
import org.pmw.tinylog.labelers.TimestampLabeler;
import org.pmw.tinylog.policies.*;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.RollingFileWriter;
import org.pmw.tinylog.writers.Writer;

public class TinyLogProvider {

    public static final String LOG_FILENAME = "UndertowWebServiceLog.log";

    public static void initialize() {
        org.pmw.tinylog.Level level = Level.DEBUG;
        String parameter = System.getProperty("level");

        if (parameter != null && !parameter.isEmpty()) {
            System.out.println(String.format("Resolved 'level' parameter = '%s'", parameter));
            level = org.pmw.tinylog.Level.valueOf(parameter);
        }

        Writer writer = runningFromIntelliJ() ? new ConsoleWriter() : new RollingFileWriter(LOG_FILENAME, 20, new TimestampLabeler(), new StartupPolicy(), new SizePolicy(10_000_00));
        org.pmw.tinylog.Configurator.defaultConfig()
                .formatPattern("{context:CorrelationId} {thread} {date:yyyy-MM-dd HH:mm:ss} {level} {class_name}.{method} : {message}")
                .writer(writer)
                .level(level)
                .activate();
    }

    /*private static boolean checkIfStartedFromIDE() {
        return System.getProperty("user.dir").contains("C:\\Projects\\uService");
    }*/

    public static boolean runningFromIntelliJ() {
        String classPath = System.getProperty("java.class.path");
        boolean idea = classPath.contains("idea_rt.jar");
        System.out.println("Is app started from IDEA = " + idea);
        return idea;
    }
}
