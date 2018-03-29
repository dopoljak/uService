package com.ilirium.uService.exampleservicejar.resource;

import com.example.demo.client.dto.EndUserDTO;
import com.example.demo.client.dto.WrapperDTO;
import com.example.demo.database.entities.EndUser;
import com.example.demo.database.repositories.EndUserRepository;
import com.ilirium.database.commons.AbstractDO;
import com.ilirium.database.commons.PageRequest;
import com.ilirium.uService.exampleservicejar.mappers.EndUserMapper;
import com.ilirium.webservice.exceptions.AppException;
import com.ilirium.webservice.exceptions.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


/**
 * @author dpoljak
 */
@Api(value = "/", description = "EndUser resource", tags = "EndUser")
@Path("/endusers")
@RequestScoped
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EndUserResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EndUserResource.class);

    @Inject
    private Event<AbstractDO> domainObjectEvents;

    @Inject
    private EndUserMapper endUserMapper;

    @Inject
    private EndUserRepository endUserRepository;

    @ApiOperation(
            value = "Get list of EndUsers2",
            notes = "Get list of EndUsers2 ...",
            response = WrapperDTO.class
    )
    @GET
    @Path("wrapped_and_paged")
    public WrapperDTO<Collection<EndUserDTO>> getEndUser(
            @QueryParam(value = "offset") //@DefaultValue("0")
                    Integer offset,
            @QueryParam(value = "limit") //@DefaultValue("50")
                    Integer limit,
            @QueryParam(value = "page") Integer page) {

        LOGGER.info(">>  get(offset = {}, limit = {}, page = {})", offset, limit, page);

        PageRequest pageRequest = PageRequest.defaultIfWrongConf(offset, limit, page);
        LOGGER.info("CalculatedOffset = {}, Limit = {}", pageRequest.getCalculatedOffset(), pageRequest.getLimit());

        Collection<EndUser> endUsers = endUserRepository.findPagedOrdered(pageRequest, null);
        Collection<EndUserDTO> endUserDTOs = endUserMapper.map(endUsers);
        Long count = endUserRepository.countAll();

        LOGGER.info("<<  get");

        return new WrapperDTO<>(count, endUserDTOs);
    }

    @ApiOperation(
            value = "Get list of EndUsers",
            notes = "Get list of EndUsers ...",
            response = EndUserDTO.class,
            responseContainer = "Collection"
    )
    @GET
    @Path("all")
    public Collection<EndUserDTO> getEndUsers() {
        Collection<EndUser> endUsers = endUserRepository.getAll();
        return endUserMapper.map(endUsers);
    }

    @ApiOperation(
            value = "Create EndUsers",
            notes = "Create EndUsers ...",
            response = EndUserDTO.class
    )
    @POST
    public EndUserDTO createEndUser(EndUserDTO endUserDTO) {

        if (endUserDTO.getId() != null) {
            throw new AppException(CommonException.ENDUSER_ID_MUST_BE_NULL);
        }

        EndUser endUser = endUserMapper.map(endUserDTO);
        endUserRepository.persist(endUser);
        domainObjectEvents.fire(endUser);
        endUserDTO.setId(endUser.getId());
        return endUserDTO;
    }
}
