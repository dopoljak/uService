package com.ilirium.database.resources;

import com.ilirium.database.flyway.SchemaVersion;
import com.ilirium.database.flyway.SchemaVersionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.List;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

//@Api(value = "/", tags = "System")
@Path("/system")
@RequestScoped
@Transactional
public class RepositorySystemResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RepositorySystemResource.class);

    @Inject
    private SchemaVersionRepository schemaVersionRepository;

    @Inject
    private EntityManager entityManager;

    //@ApiOperation(value = "Get schema_version", notes = "Get schema_version", response = SchemaVersion.class)
    @GET
    @Path("/schema_version")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SchemaVersion> getDatabaseSchemaVersions() {
        LOGGER.info(">> getDatabaseSchemaVersions()");

        final List<SchemaVersion> response = schemaVersionRepository.getSchemaVersions();

        LOGGER.info("<< getDatabaseSchemaVersions({})", response);
        return (response);
    }

    //@ApiOperation(value = "Get driver_name", notes = "Get driver_name", response = String.class)
    @GET
    @Path("/driver_name")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDriverName() {
        try {
            DataSource dataSource = (DataSource) entityManager.getEntityManagerFactory().getProperties().get("javax.persistence.jtaDataSource");
            Connection connection = dataSource.getConnection();
            try {
                return "MetaData -> DriverNamer : " + connection.getMetaData().getDriverName();
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
    }
}