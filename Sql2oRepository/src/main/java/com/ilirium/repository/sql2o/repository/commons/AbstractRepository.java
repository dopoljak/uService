package com.ilirium.repository.sql2o.repository.commons;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 *
 * @author dpoljak
 */
public abstract class AbstractRepository<E> {

    public Sql2o getSql2o() {
        return Sql2oSingleton.INSTANCE.getSql2o();
    }

    public Class<E> returnEntityClass() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    // TODO: this is not good patern, transaction should be maintained in upper-layer, refactor! :)
    protected Long insert(E entity, String insert) {
        try (Connection connection = getSql2o().beginTransaction()) {
            Long id = (Long) connection.createQuery(insert)
                    .bind(entity)
                    .executeUpdate()
                    .getKey();
            connection.commit();
            return id;
        }
    }

    protected abstract String selectAllQuery();

    public Collection<E> getAll(Pagination pagination) {
        try (Connection connection = getSql2o().open()) {
            return connection
                    .createQuery(selectAllQuery())
                    .addParameter("limit", pagination.limit())
                    .addParameter("offset", pagination.offset())
                    .executeAndFetch(returnEntityClass());
        }
    }
}
