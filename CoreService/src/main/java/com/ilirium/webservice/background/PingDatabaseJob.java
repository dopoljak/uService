package com.ilirium.webservice.background;

import com.ilirium.basic.db.*;

import javax.inject.*;
import java.util.*;

public class PingDatabaseJob extends BackgroundJob {

    @Inject
    private SchemaVersionRepository schemaVersionRepository;

    @Override
    protected void runJob() {

        List<SchemaVersion> schemaVersions = schemaVersionRepository.getSchemaVersions();
        schemaVersions.sort((sv1, sv2) -> sv2.getVersion().compareTo(sv1.getVersion()));
        getLogger().info("Schema version: {}", schemaVersions.get(0));

    }

    @Override
    protected String getJobName() {
        return "PingDatabase";
    }

    @Override
    protected String getHour() {
        return "*";
    }

    @Override
    protected String getMinute() {
        return "*";
    }
}
