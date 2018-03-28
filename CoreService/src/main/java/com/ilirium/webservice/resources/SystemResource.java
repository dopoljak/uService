package com.ilirium.webservice.resources;

import com.ilirium.database.flyway.SchemaVersion;
import com.ilirium.database.flyway.SchemaVersionRepository;
import com.ilirium.webservice.commons.AppConfiguration;
import com.ilirium.webservice.commons.DateUtils;
import com.ilirium.webservice.commons.VersionUtils;
import com.ilirium.webservice.filters.LoggingFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpoljak
 */
@Api(value = "/", tags = "System")
@Path("/system")
@RequestScoped
@Transactional
public class SystemResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SystemResource.class);
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
