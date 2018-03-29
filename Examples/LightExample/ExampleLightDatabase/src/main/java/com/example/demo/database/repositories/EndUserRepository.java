package com.example.demo.database.repositories;

import com.example.demo.database.entities.EndUser;
import com.ilirium.repository.sql2o.repository.commons.AbstractRepository;
import org.sql2o.Connection;

/**
 * @author dpoljak
 */
public class EndUserRepository extends AbstractRepository<EndUser> {

    private static final String CREATE_END_USER_TABLE = "create table ENDUSER(id bigint identity primary key, username varchar(50), password varchar(50))";
    private static final String INSERT_QUERY = "insert into ENDUSER(id, username,password) values (nextval('ENDUSER_SEQ'),:username,:password)";
    //private static final String SELECT_ALL_QUERY = "SELECT id, username, password FROM ENDUSER";
    private static final String SELECT_ALL_ENTITIES = "SELECT * FROM ENDUSER LIMIT :limit OFFSET :offset";

    public void createTable() {
        try (Connection con = getSql2o().beginTransaction()) {
            con.createQuery(CREATE_END_USER_TABLE)
                    .executeUpdate();
            con.commit();
        }
    }

    public void insert(EndUser entity) {
        Long id = insert(entity, INSERT_QUERY);
        entity.setId(id);
    }

    @Override
    protected String selectAllQuery() {
        return SELECT_ALL_ENTITIES;
    }
}
