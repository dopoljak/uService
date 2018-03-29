package com.ilirium.repository.sql2o.repository;

import com.ilirium.repository.sql2o.repository.commons.AbstractRepository;
import com.ilirium.repository.sql2o.entity.SchemaVersion;

/**
 *
 * @author dpoljak
 */
public class SchemaVersionRepository extends AbstractRepository<SchemaVersion> {

    private static final String SELECT_ALL = "SELECT \"version\", \"installedRank\", \"description\", \"type\", \"script\", \"checksum\", \"installedBy\", \"installedOn\", \"executionTime\", \"success FROM \"flyway_schema_history\"";

    @Override
    protected String selectAllQuery() {
        return SELECT_ALL;
    }
}
