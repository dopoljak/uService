package com.ilirium.database.flyway;

import com.ilirium.database.commons.AbstractRepository;
import com.mysema.query.types.EntityPath;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class SchemaVersionRepository extends AbstractRepository<SchemaVersion> {

    public SchemaVersionRepository() {
    }

    public List<SchemaVersion> getSchemaVersions() {
        return em.createQuery("SELECT sc FROM SchemaVersion sc").getResultList();
    }

    @Override
    protected EntityPath<SchemaVersion> getQClass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
