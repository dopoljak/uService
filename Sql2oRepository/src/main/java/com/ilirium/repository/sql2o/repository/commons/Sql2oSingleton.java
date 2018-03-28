package com.ilirium.repository.sql2o.repository.commons;

import org.sql2o.Sql2o;

public enum Sql2oSingleton {

    INSTANCE;

    private Sql2o sql2o;

    public void set(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Sql2o getSql2o() {
        return sql2o;
    }
}
