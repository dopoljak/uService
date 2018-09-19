package com.ilirium.webservice.resources;

import com.ilirium.basic.*;
import com.ilirium.basic.db.*;
import com.ilirium.basicee.*;
import com.ilirium.webservice.commons.*;
import com.ilirium.webservice.filter.*;
import io.swagger.annotations.*;

import javax.enterprise.context.*;
import javax.inject.*;
import javax.servlet.*;
import javax.transaction.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Api(value = "/system", tags = "System")
@Path("/system")
@RequestScoped
@Transactional
public class CoreSystemResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CoreSystemResource.class);
    private static final long START_TIME = System.currentTimeMillis();

    @Inject
    private AppConfiguration appConfiguration;

    @Context
    private ServletContext servletContext;

    @ApiOperation(value = "Get service status", response = Map.class)
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDefault() {
        LOGGER.info(">> getDefault()");

        Map<String, Object> version = new HashMap<>();
        version.put(Const.CORRELATION_ID, LoggingFilter.getCorrelationId());
        version.put("Start-Time", DateUtils.formatMillis(START_TIME));
        version.put("Up-Time", DateUtils.formatElapsedMillis(System.currentTimeMillis() - START_TIME));
        version.put("Manifest", VersionUtils.readWarManifest(servletContext));

        LOGGER.info("<< getDefault({})", version);
        return version;
    }

    @Inject
    private SchemaVersionRepository schemaVersionRepository;

    @ApiOperation(value = "Get schema_version", notes = "Get schema_version", response = SchemaVersion.class)
    @GET
    @Path("/schema_version")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SchemaVersion> getDatabaseSchemaVersions() {
        LOGGER.info(">> getDatabaseSchemaVersions()");

        final List<SchemaVersion> response = schemaVersionRepository.getSchemaVersions();

        LOGGER.info("<< getDatabaseSchemaVersions({})", response);
        return (response);
    }

//    @Inject
//    private EntityManager entityManager;
//    //@ApiOperation(value = "Get driver_name", notes = "Get driver_name", response = String.class)
//    @GET
//    @Path("/driver_name")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getDriverName() {
//        try {
//            DataSource dataSource = (DataSource) entityManager.getEntityManagerFactory().getProperties().get("javax.persistence.jtaDataSource");
//            Connection connection = dataSource.getConnection();
//            try {
//                return "MetaData -> DriverNamer : " + connection.getMetaData().getDriverName();
//            } finally {
//                connection.close();
//            }
//        } catch (Exception e) {
//            LOGGER.error("", e);
//            throw new RuntimeException(e);
//        }
//    }
//

    /*
    @ApiOperation(value = "Get service status", response = HealthStatus.class)
    @GET
    @Path("/diskspace")
    @Health
    public HealthStatus checkDiskspace() {
        File path = new File(System.getProperty("user.home"));
        long freeBytes = path.getFreeSpace();
        long threshold = 1024 * 1024 * 100; // 100mb
        return freeBytes > threshold ?
                HealthStatus.
                        named("diskspace")
                        .up()
                        .withAttribute("freebytes", freeBytes) :
                HealthStatus.
                        named("diskspace")
                        .down()
                        .withAttribute("freebytes", freeBytes);
    }
    @ApiOperation(value = "Get service time", response = HealthStatus.class)
    @GET
    @Path("/time")
    @Health
    public HealthStatus checkSomethingElse() {
        return HealthStatus
                .named("something-else")
                .up()
                .withAttribute("date", new Date().toString())
                .withAttribute("time", System.currentTimeMillis());
    }
    */
}



/*
@Api(value = "/", tags = "System")
@Path("/system")
@RequestScoped
@Transactional
public class CoreSystemResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CoreSystemResource.class);
    private static final long START_TIME = System.currentTimeMillis();

    @Inject
    private SchemaVersionRepository schemaVersionRepository;

    @Inject
    private AppConfiguration appConfiguration;

    @Context
    private ServletContext servletContext;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDefault() {
        LOGGER.info(">> getDefault()");

        final Map<String, Object> response = getVersionMap();
        response.put("Database", schemaVersionRepository.getSchemaVersions());

        LOGGER.info("<< getDefault({})", response);
        return (response);
    }

    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getVersion() {
        return getVersionMap();
    }

    @ApiOperation(
            value = "Get schema_version",
            notes = "Get schema_version",
            response = SchemaVersion.class
    )
    @GET
    @Path("/schema_version")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SchemaVersion> getDatabaseSchemaVersions() {
        LOGGER.info(">> getDatabaseSchemaVersions()");

        final List<SchemaVersion> response = schemaVersionRepository.getSchemaVersions();

        LOGGER.info("<< getDatabaseSchemaVersions({})", response);
        return (response);
    }


    private Map<String, Object> getVersionMap() {
        Map<String, Object> version = new HashMap<>();
        version.put(LoggingFilter.CORRELATION_ID, LoggingFilter.getCorrelationId());
        version.put("Start-Time", DateUtils.formatMillis(START_TIME));
        version.put("Up-Time", DateUtils.formatElapsedMillis(System.currentTimeMillis() - START_TIME));
        version.put("Manifest", VersionUtils.readWarManifest(servletContext));
        return version;
    }
}
*/