package com.ilirium.uservice.undertow.voidpack.commons;

import com.ilirium.uservice.undertowserver.commons.Config;

public class Singletons {

    //private static Database database;
    private static Config config;

    public static void setConfig(Config config) {
        Singletons.config = config;
    }

    public static Config getConfig() {
        return config;
    }


    /*
    private static BackgroundJobs jobs;

    public static void setJobs(BackgroundJobs backgroundJobs) {
        Singletons.jobs = backgroundJobs;
    }

    public static BackgroundJobs getJobs() {
        return jobs;
    }*/

    /*
    public static void setDatabase(Database database) {
        Singletons.database = database;
    }


    public static Database getDatabase() {
        return database;
    }
    */
}
