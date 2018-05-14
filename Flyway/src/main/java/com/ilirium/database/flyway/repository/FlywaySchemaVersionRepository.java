package com.ilirium.database.flyway.repository;

import com.ilirium.basic.db.*;
import com.ilirium.database.flyway.entity.*;
import com.ilirium.database.templates.*;
import com.mysema.query.types.*;

import java.util.*;

public class FlywaySchemaVersionRepository extends AbstractRepository<FlywaySchemaVersion> implements SchemaVersionRepository {

    @Override
    protected EntityPath<FlywaySchemaVersion> getQClass() {
        return null;
    }

    @Override
    public List<SchemaVersion> getSchemaVersions() {
        return null;
    }
}
