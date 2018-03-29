package com.ilirium.database.commons;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @param <E>
 * @author dpoljak
 */
public abstract class AbstractRepository<E> {

    @Inject
    protected EntityManager entityManager;

    public AbstractRepository() {
    }

    protected JPAQuery query() {
        return new JPAQuery(entityManager);
    }

    @SuppressWarnings("unchecked")
    public Class<E> returnEntityClass() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    public E findById(Long id) {
        return entityManager.find(returnEntityClass(), id);
    }

    public void persist(E e) {
        this.entityManager.persist(e);
    }

    public void remove(E e) {
        this.entityManager.remove(e);
    }

    public E merge(E e) {
        return this.entityManager.merge(e);
    }

    public long countAll() {
        return query()
                .from(getQClass())
                .count();
    }

    public List<E> findPagedOrdered(PageRequest pageRequest, OrderRequest orderRequest) {
        return query()
                .from(getQClass())
                .offset(pageRequest.getCalculatedOffset())
                .limit(pageRequest.getLimit())
                //.orderBy(orderRequest.getSpecifier())
                .list(getQClass());
    }

    protected abstract EntityPath<E> getQClass();

}
